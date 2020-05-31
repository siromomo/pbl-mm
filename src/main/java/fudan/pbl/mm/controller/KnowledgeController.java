package fudan.pbl.mm.controller;

import fudan.pbl.mm.controller.request.auth.KnowledgeRequest;
import fudan.pbl.mm.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    @Autowired
    public KnowledgeController(KnowledgeService knowledgeService){
        this.knowledgeService = knowledgeService;
    }

    @RequestMapping("/addKnowledge")
    public ResponseEntity<?> addKnowledge(KnowledgeRequest request){
        return ResponseEntity.ok(knowledgeService.addKnowledge(request));
    }

    @RequestMapping("/modifyKnowledge")
    public ResponseEntity<?> modifyKnowledge(KnowledgeRequest request){
        return ResponseEntity.ok(knowledgeService.modifyKnowledge(request));
    }

    @RequestMapping("/deleteKnowledge")
    public ResponseEntity<?> deleteKnowledge(Long id){
        return ResponseEntity.ok(knowledgeService.deleteKnowledge(id));
    }

    @RequestMapping("/getAllKnowledge")
    public ResponseEntity<?> getAllKnowledge(){
        return ResponseEntity.ok(knowledgeService.getKnowledgeList());
    }
}
