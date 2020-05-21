package fudan.pbl.mm.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KnowledgeServiceTest {
    @Autowired
    KnowledgeService knowledgeService;

  //  @Test
    public void init(){
        knowledgeService.init();
    }
}