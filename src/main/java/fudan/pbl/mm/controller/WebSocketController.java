package fudan.pbl.mm.controller;


import com.google.common.collect.Lists;
import fudan.pbl.mm.controller.request.ChatMessage;
import fudan.pbl.mm.controller.request.PositionMessage;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.Position;
import fudan.pbl.mm.domain.Virus;
import fudan.pbl.mm.repository.CellRepository;
import fudan.pbl.mm.service.CellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@EnableScheduling
public class WebSocketController {
    //注入SimpMessagingTemplate 用于点对点消息发送
    //@Autowired
    private SimpMessagingTemplate messagingTemplate;
    private CellRepository cellRepository;
    public static Map<Cell, Position> cellPositionMap = new ConcurrentHashMap<>();
    public static Map<Virus, Position> virusPositionMap = new ConcurrentHashMap<>();


    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostConstruct
    public void init(){
        cellPositionMap = new ConcurrentHashMap<>();
        virusPositionMap = new ConcurrentHashMap<>();
        while(virusPositionMap.size() < CellService.INIT_VIRUS_NUM) {
            virusPositionMap.put(new Virus(), new Position());
        }
        List<Cell> cells = Lists.newArrayList(cellRepository.findAll());
        for(Cell cell : cells){
            cellPositionMap.put(cell, new Position());
        }
    }

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, CellRepository cellRepository){
        this.messagingTemplate = messagingTemplate;
        this.cellRepository = cellRepository;
    }


    // 这里是客户端发送消息对应的路径，
    // 等于configureMessageBroker中配置的setApplicationDestinationPrefixes + 这路径
    // 即 /app/updatePosition
    @MessageMapping("/updatePosition")
    public void updatePosition(PositionMessage message) {
        System.out.println("websocket get message:" + message.getX() + "," + message.getY() + "," + message.getZ());
        Cell cell = cellRepository.findCellById(message.getObjectId());
        if(!cell.isActive()) return;
        Position position = new Position(message.getX(), message.getY(), message.getZ());
        cellPositionMap.put(cell, position);
        /*messagingTemplate.convertAndSend("/topic/positionToAll",
                new ResponseObject<>(200, "success", message));*/
        // 订阅 /topic/positionToAll 实现公告
        checkVirus(cell);
        sendUpdateCellAndVirusResp();
    }

    private void sendUpdateCellAndVirusResp(){
        Map<String, Object> resp = new HashMap<>();
        resp.put("cellPositionMap", cellPositionMap);
        resp.put("virusPositionMap", virusPositionMap);
        ResponseObject<Map> responseObject = new ResponseObject<>(200, "success", resp);
        messagingTemplate.convertAndSend("/topic/updateCellAndVirus", responseObject);
    }

    @RequestMapping("/initVirus")
    public ResponseEntity<?> initVirus(){
        return ResponseEntity.ok(new ResponseObject<>(200, "success", virusPositionMap));
    }

    // 客户端：/app/chatMessageToOne
    @MessageMapping("/chatMessageToOne")
    public void sendMessageToOne(ChatMessage message){
        String user = String.valueOf(message.getToId());
        messagingTemplate.convertAndSendToUser(user, "/toOne",
                new ResponseObject<>(200, "success", message));
        // 订阅 /user/userId/toOne 实现点对点
    }

    @Scheduled(fixedRate = 1000)
    public void updateVirus(){
        for(Virus virus : virusPositionMap.keySet()){
            Position pos = virusPositionMap.get(virus);
            pos.randomUpdate();
            virusPositionMap.put(virus, pos);
        }
        for(Cell cell : cellPositionMap.keySet()) checkVirus(cell);
        sendUpdateCellAndVirusResp();
    }

    private synchronized void checkVirus(Cell cell){
        Position cellPos = cellPositionMap.get(cell);
        for(Virus virus : virusPositionMap.keySet()){
            Position virusPos = virusPositionMap.get(virus);
            if(virusPos.calculateDistance2d(cellPos) <= virus.getRadius()){
                System.out.println("cell touched virus");
                if(!cell.isPackFilled()){
                    cellPositionMap.remove(cell);
                    cell.setActive(false);
                }else{
                    virusPositionMap.remove(virus);
                    int level = cell.getLevel();
                    cell.setLevel(level+1);
                }
                cellRepository.save(cell);
            }
        }
        while(virusPositionMap.keySet().size() < CellService.INIT_VIRUS_NUM){
            virusPositionMap.put(new Virus(), new Position());
        }
    }
}
