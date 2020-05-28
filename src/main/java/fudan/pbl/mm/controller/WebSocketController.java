package fudan.pbl.mm.controller;


import fudan.pbl.mm.controller.request.*;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.*;
import fudan.pbl.mm.repository.*;
import fudan.pbl.mm.service.CellService;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@EnableScheduling
public class WebSocketController {
    //注入SimpMessagingTemplate 用于点对点消息发送
    //@Autowired
    private SimpMessagingTemplate messagingTemplate;
    private PackRepository packRepository;
    private UserRepository userRepository;
    private CellInfoRepository cellInfoRepository;
    private KnowledgeRepository knowledgeRepository;
    private ChoiceQuestionRepository choiceQuestionRepository;
    public static Map<User, Position> cellPositionMap = new ConcurrentHashMap<>();
    private static Set<User> loadFinishSet = new HashSet<>();
    private static Map<Long, User> idUserMap = new ConcurrentHashMap<>();
    public static Map<Virus, Position> virusPositionMap = new ConcurrentHashMap<>();
    private Random random;
    private ChoiceQuestion currQuestion;


    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private Pack pack;
    private static final int NUM_OF_TYPES = 8;
    private static final int NUM_OF_LEAST_PLAYER = 2;
    private static final int NUM_OF_MAX_PLAYER = 4;
    private int currentNumOfUser = 0;
    private int currentNumOfLoadedUser = 0;

    @PostConstruct
    public void init() throws IOException {
        cellPositionMap = new ConcurrentHashMap<>();
        virusPositionMap = new ConcurrentHashMap<>();
        idUserMap = new ConcurrentHashMap<>();
        loadFinishSet = new HashSet<>();
        while (virusPositionMap.size() < CellService.INIT_VIRUS_NUM) {
            virusPositionMap.put(new Virus(), new Position());
        }
        random = new Random();
        BufferedImage img = ImageIO.read(new File("./terrain.jpg"));
        Raster ra = img.getData();
        Rectangle rect = ra.getBounds();
        //System.out.println(rect.height);
        //System.out.println(rect.width);
        int nTemp[] = new int[rect.height*rect.width];
        Position.map = ra.getPixels(0,0,rect.width, rect.height,nTemp);
//        for(int i = 0;i<100;i++)
//            System.out.print(Position.map[500-i]+" ");

    }

    private void initPack() {
        pack = new Pack();
        pack.setHp(100);
        packRepository.save(pack);
        pack = packRepository.findFirstByHpGreaterThanOrderByIdDesc(0);
        System.out.println("packId: " + pack.getId());
    }

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate,
                               PackRepository packRepository, UserRepository userRepository,
                               CellInfoRepository cellInfoRepository,
                               ChoiceQuestionRepository choiceQuestionRepository,
                               KnowledgeRepository knowledgeRepository) {
        this.messagingTemplate = messagingTemplate;
        this.packRepository = packRepository;
        this.userRepository = userRepository;
        this.cellInfoRepository = cellInfoRepository;
        this.choiceQuestionRepository = choiceQuestionRepository;
        this.knowledgeRepository = knowledgeRepository;
    }


    @MessageMapping("/connectToServer")
    public void connect(PositionMessage message) {
        if (currentNumOfUser >= NUM_OF_LEAST_PLAYER) {
            return;
        }
        User user = userRepository.findUserById(message.getObjectId());
        Position position = new Position();
        boolean exist = false;
        for (User user2 : cellPositionMap.keySet()) {
            if (user2.getId().equals(user.getId())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            cellPositionMap.put(user, position);
            idUserMap.put(user.getId(), user);
            currentNumOfUser++;
            messagingTemplate.convertAndSend("/topic/currentNumOfUser",
                    new ResponseObject<>(200, "success", currentNumOfUser));
        }
        if (currentNumOfUser == NUM_OF_LEAST_PLAYER) {
            while (virusPositionMap.size() < CellService.INIT_VIRUS_NUM) {
                virusPositionMap.put(new Virus(), new Position());
            }
            if (pack == null) initPack();
            StartGameResponse startGameResponse = new StartGameResponse(pack,
                    new HashMap<>(cellPositionMap),
                    new HashMap<>(virusPositionMap), user);
            startGameResponse.setUserSet(new HashSet<>(cellPositionMap.keySet()));
            messagingTemplate.convertAndSend("/topic/startGame", new ResponseObject<>(
                    200, "success", startGameResponse));
        }
    }

    @MessageMapping("/dropHp")
    public void dropHp(DropHpMessage drop){
        pack.setHp(pack.getHp() - drop.getDropNum());
        messagingTemplate.convertAndSend("/topic/updateHp",
                new ResponseObject<>(200, "success",
                pack.getHp()));
        if(pack.getHp() <= 0){
            cleanCurrentGame();
        }
    }

    @MessageMapping("/clickVirus")
    public void clickVirus(ClickVirusMessage message){
        if(currentNumOfLoadedUser < NUM_OF_LEAST_PLAYER) return;
        int rand = random.nextInt(3);
        switch (rand){
            case 0:
                collectKnowledge(getRandomKnowledge());
            break;
            case 1:
                getRandomQuestion(message.getUserId());
            break;
            case 2:
                collectCellInfo(getCellInfoByType(message.getCellType()));
        }
        for(Virus virus : virusPositionMap.keySet()){
            if(virus.hashCode() == (message.getVirusId())){
                System.out.println("virus" + virus.getHashId());
                System.out.println("before: "+ virusPositionMap.get(virus).getX() + " " + virusPositionMap.get(virus).getY()
                        + " " + virusPositionMap.get(virus).getZ());
                virusPositionMap.remove(virus);
                virusPositionMap.put(virus, new Position());
                System.out.println("remove");
                System.out.println("after: " + virusPositionMap.get(virus).getX() + " " + virusPositionMap.get(virus).getY()
                        + " " + virusPositionMap.get(virus).getZ());
                break;
            }
        }
    }

    @MessageMapping("/answerQuestion")
    public void answerQuestion(AnswerQuestionMessage message){
        String correct = currQuestion.getCorrectChoice();
        correct = correct.trim();
        if(currQuestion == null || !currQuestion.getId().equals(message.getQuestionId())){
            System.out.println("not this question");
            return;
        }
        if(!correct.equals(message.getAnswer())){
            System.out.println("wrong answer");
            messagingTemplate.convertAndSend("/topic/" + message.getUserId() + "/wrongAnswer",
                    new ResponseObject<>(200, "success", message.getQuestionId()));
            return;
        }
        messagingTemplate.convertAndSend("/topic/" + message.getUserId() + "/correctAnswer",
                new ResponseObject<>(200, "success", message.getQuestionId()));
        answerQuestion(currQuestion);
    }

    @MessageMapping("/loadFinish")
    public void loadFinish(PositionMessage message){
        if(currentNumOfLoadedUser >= NUM_OF_LEAST_PLAYER) return;
        User user = userRepository.findUserById(message.getObjectId());
        //Position position = new Position(message.getX(), message.getY(), message.getZ(), message.getRotation());
        if(!loadFinishSet.contains(user)) {
            loadFinishSet.add(user);
            currentNumOfLoadedUser++;
            if (currentNumOfLoadedUser >= NUM_OF_LEAST_PLAYER) {
                messagingTemplate.convertAndSend("/topic/startGame",
                        new ResponseObject<>(200, "success", "all users load finish"));
            }
        }
    }

    @MessageMapping("/quitGame")
    public void quitGame(PositionMessage message) {
        User leave = null;
        for (User u : cellPositionMap.keySet()) {
            if (u.getId().equals(message.getObjectId())) {
                cellPositionMap.remove(u);
                idUserMap.remove(u.getId());
                loadFinishSet.remove(u);
                leave = u;
                currentNumOfUser--;
                currentNumOfLoadedUser--;
                break;
            }
        }
        if (leave != null)
            messagingTemplate.convertAndSend("/topic/userQuit", leave);
    }

    @RequestMapping("/cleanCurrentGame")
    public ResponseEntity<?> cleanCurrentGame() {
        cellPositionMap = new ConcurrentHashMap<>();
        loadFinishSet = new HashSet<>();
        messagingTemplate.convertAndSend("/topic/gameOver",
                new ResponseObject<>(200, "success", pack != null
                        && pack.getHp() > 0
                        && pack.isFilled()));
        packRepository.save(pack);
        pack = null;
        currentNumOfLoadedUser = 0;
        currentNumOfUser = 0;
        return ResponseEntity.ok(new ResponseObject<>(200, "success", null));
    }

    @RequestMapping("/getCurrentPack")
    public ResponseEntity<?> getCurrentPack() {
        return ResponseEntity.ok(new ResponseObject<>(200, "success", pack));
    }

    // 这里是客户端发送消息对应的路径，
    // 等于configureMessageBroker中配置的setApplicationDestinationPrefixes + 这路径
    // 即 /app/updatePosition
    @MessageMapping("/updatePosition")
    public void updatePosition(PositionMessage message) {
        //System.out.println("websocket get message:" + message.getX() + "," + message.getY() + "," + message.getZ());
        User user = idUserMap.get(message.getObjectId());
        Position position = cellPositionMap.get(user);
        position.setX(message.getX());
        position.setY(message.getY());
        position.setZ(message.getZ());
        position.setRotation(message.getRotation());
        cellPositionMap.put(user, position);
        /*checkVirus(user);
        sendUpdateCellAndVirusResp("/topic/updateCellAndVirus");*/
    }

    private void sendUpdateCellAndVirusResp(String des) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("cellPositionMap", cellPositionMap);
        resp.put("virusPositionMap", virusPositionMap);
        ResponseObject<Map> responseObject = new ResponseObject<>(200, "success", resp);
        messagingTemplate.convertAndSend(des, responseObject);
    }

    @RequestMapping("/initVirus")
    public ResponseEntity<?> initVirus() {
        return ResponseEntity.ok(new ResponseObject<>(200, "success", virusPositionMap));
    }

    // 客户端：/app/chatMessageToOne
    @MessageMapping("/chatMessageToOne")
    public void sendMessageToOne(ChatMessage message) {
        String user = String.valueOf(message.getToId());
        messagingTemplate.convertAndSend( "/topic/" + user + "/toOne",
                new ResponseObject<>(200, "success", message));
        // 订阅 /user/userId/toOne 实现点对点
    }

    @MessageMapping("/chatMessageToAll")
    public void sendMessageToAll(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/toAll", message);
    }

    @MessageMapping("/collectCellInfo")
    public void collectCellInfo(CellInfo cellInfo) {
        pack.addToCellInfoSet(cellInfo);
        messagingTemplate.convertAndSend("/topic/updatePack",
                new ResponseObject<>(200, "success", pack));
    }

    public void answerQuestion(ChoiceQuestion question){
        pack.addToChoiceQuestionSet(question);
        messagingTemplate.convertAndSend("/topic/updatePack",
                new ResponseObject<>(200, "success", pack));
    }


    public void collectKnowledge(Knowledge knowledge){
        pack.addToKnowledgeSet(knowledge);
        messagingTemplate.convertAndSend("/topic/updatePack",
                new ResponseObject<>(200, "success", pack));
    }

    public Knowledge getRandomKnowledge(){
        Knowledge knowledge = knowledgeRepository.findRandomKnowledge();
        System.out.println("get random knowledge:" + knowledge.getContent());
        messagingTemplate.convertAndSend("/topic/getRandomKnowledge",
                new ResponseObject<Knowledge>(200, "success",
                knowledge));
        return knowledge;
    }

    public ChoiceQuestion getRandomQuestion(Long userId){
        ChoiceQuestion question = choiceQuestionRepository.findRandomQuestion();
        currQuestion = question;
        System.out.println("send to " + "/topic/" +
                userId + "/getRandomQuestion");
        messagingTemplate.convertAndSend( "/topic/" +
                        userId + "/getRandomQuestion",
                new ResponseObject<>(200, "success",
                        question));
        return question;
    }

    public CellInfo getCellInfoByType(String type){
        CellInfo cellInfo = cellInfoRepository.findCellInfoByType(type);
        messagingTemplate.convertAndSend("/topic/findCellInfoByType",
                new ResponseObject<>(200, "success",
                        cellInfo));
        return cellInfo;
    }

    @Scheduled(fixedRate = 1000)
    public void updateVirus() {
        if(currentNumOfLoadedUser >= NUM_OF_LEAST_PLAYER) {
            for (Virus virus : virusPositionMap.keySet()) {
                Position pos = virusPositionMap.get(virus);
                pos.randomUpdate();
                virusPositionMap.put(virus, pos);
            }
           // for (User user : cellPositionMap.keySet()) checkVirus(user);
            sendUpdateCellAndVirusResp("/topic/updateCellAndVirus");
        }

        if(pack != null && pack.isFilled()){
            cleanCurrentGame();
        }
    }

    private synchronized void checkVirus(User user) {
        Position cellPos = cellPositionMap.get(user);
        for (Virus virus : virusPositionMap.keySet()) {
            Position virusPos = virusPositionMap.get(virus);
            if (virusPos.calculateDistance(cellPos) <= virus.getRadius()) {
                System.out.println("cell touched virus");
                if (!pack.isFilled()) {
                    pack.dropHp();
                    if (pack.getHp() <= 0) {
                        /*messagingTemplate.convertAndSend("/topic/gameOver", new ResponseObject<>(200, "success", "game over"));
                        cleanCurrentGame();
                        return;*/
                    }
                    packRepository.save(pack);
                } else {
                    virusPositionMap.remove(virus);
                }
            }
        }
        while (virusPositionMap.keySet().size() < CellService.INIT_VIRUS_NUM) {
            virusPositionMap.put(new Virus(), new Position());
        }
    }
}
