package fudan.pbl.mm.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChoiceQuestionServiceTest {
    @Autowired
    private ChoiceQuestionService choiceQuestionService;

    @Test
    void read() {
        choiceQuestionService.read();
    }
}