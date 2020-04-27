package fudan.pbl.mm.controller;

import fudan.pbl.mm.service.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackController {
    private PackService packService;

    @Autowired
    public PackController(PackService packService){
        this.packService = packService;
    }

    @RequestMapping("/collectCellInfo")
    public ResponseEntity<?> collectCellInfo(@RequestParam long cellId, @RequestParam String cellInfoType){
        return ResponseEntity.ok(packService.collectCellInfo(cellInfoType, cellId));
    }
}
