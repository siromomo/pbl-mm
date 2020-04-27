package fudan.pbl.mm.service;

import com.google.common.collect.Lists;
import com.sun.deploy.nativesandbox.comm.Response;
import fudan.pbl.mm.controller.WebSocketController;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Cell;
import fudan.pbl.mm.domain.Pack;
import fudan.pbl.mm.domain.Position;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.repository.CellRepository;
import fudan.pbl.mm.repository.PackRepository;
import fudan.pbl.mm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellService {
    private UserRepository userRepository;
    private CellRepository cellRepository;
    private PackRepository packRepository;
    public static final int CELL_INIT_MAX_LEVEL = 5;
    public static final int INIT_VIRUS_NUM = 5;

    @Autowired
    public CellService(UserRepository userRepository, CellRepository cellRepository, PackRepository packRepository){
        this.userRepository = userRepository;
        this.packRepository = packRepository;
        this.cellRepository = cellRepository;
    }

    public ResponseObject<Cell> addCellToUser(String type, String nickname, String username){
        Cell cell = cellRepository.findCellByNickname(nickname);
        if(cell != null){
            return new ResponseObject<>(404, "cell exists", null);
        }
        User user = userRepository.findByUsername(username);
        if(user == null){
            return new ResponseObject<>(404, "user does not exist", null);
        }
        cell = new Cell();
        cell.setType(type);
        cell.setNickname(nickname);
        user.addToCells(cell);
        Pack pack = new Pack();
        pack.setCell(cell);
        cell.setPack(pack);
        WebSocketController.cellPositionMap.put(cell, new Position());
        cellRepository.save(cell);
        userRepository.save(user);
        packRepository.save(pack);
        return new ResponseObject<>(200, "success", cell);
    }

    public ResponseObject<Cell> restartCell(String cellId, String username){
        Cell cell = cellRepository.findCellById(Long.parseLong(cellId));
        if(cell == null){
            return new ResponseObject<>(404, "cell does not exist", null);
        }
        User user = userRepository.findByUsername(username);
        if(user == null){
            return new ResponseObject<>(404, "user does not exist", null);
        }
        cell.setLevel(cell.getInitLevel());
        cell.setActive(true);
        user.removeFromCells(cell);
        user.addToCells(cell);
        cellRepository.save(cell);
        userRepository.save(user);
        return new ResponseObject<>(200, "success", cell);
    }

    public ResponseObject<List<Cell>> findCellsByUser(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            return new ResponseObject<>(404, "user does not exist", null);
        }
        return new ResponseObject<>(200, "success", cellRepository.findCellsByUser(user));
    }
}
