package fudan.pbl.mm.controller;

import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.Position;
import fudan.pbl.mm.security.jwt.JwtTokenUtil;
import fudan.pbl.mm.service.CellInfoService;
import fudan.pbl.mm.service.CellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class CellController {
    private JwtTokenUtil jwtTokenUtil;
    private CellService cellService;
    private CellInfoService cellInfoService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public CellController(JwtTokenUtil jwtTokenUtil, CellService cellService, CellInfoService cellInfoService){
        this.jwtTokenUtil = jwtTokenUtil;
        this.cellService = cellService;
        this.cellInfoService = cellInfoService;
    }

    @RequestMapping("/addCellToUser")
    public ResponseEntity<?> addCellToUser(@RequestParam String type, @RequestParam String nickname,
                                           @RequestHeader String jwt_token){
        String username = jwtTokenUtil.getUsernameFromToken(jwt_token);
        ResponseObject<Cell> newCell = cellService.addCellToUser(type, nickname, username);
        WebSocketController.cellPositionMap.put(newCell.getContent(), new Position());
        return ResponseEntity.ok(newCell);
    }

    @RequestMapping("/restartCell")
    public ResponseEntity<?> restartCell(@RequestParam String cellId, @RequestHeader String jwt_token){
        String username = jwtTokenUtil.getUsernameFromToken(jwt_token);
        return ResponseEntity.ok(cellService.restartCell(cellId, username));
    }

    @RequestMapping("/findCellsByUser")
    public ResponseEntity<?> findCellsByUser(@RequestHeader String jwt_token){
        String username = jwtTokenUtil.getUsernameFromToken(jwt_token);
        return ResponseEntity.ok(cellService.findCellsByUser(username));
    }

    @RequestMapping("/findCellInfoByType")
    public ResponseEntity<?> findCellInfoByType(@RequestParam String type){
        return ResponseEntity.ok(cellInfoService.findCellInfoByType(type));
    }
}
