package fudan.pbl.mm.controller;

import fudan.pbl.mm.service.ChoiceQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemController {
    private ChoiceQuestionService choiceQuestionService;

    @Autowired
    public ProblemController(ChoiceQuestionService choiceQuestionService){
        this.choiceQuestionService = choiceQuestionService;
    }

    @RequestMapping("/getRandomQuestion")
    public ResponseEntity<?> getRandomQuestion(){
        return ResponseEntity.ok(choiceQuestionService.getRandomQuestion());
    }
}
