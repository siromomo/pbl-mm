package fudan.pbl.mm.service;

import fudan.pbl.mm.domain.Knowledge;
import fudan.pbl.mm.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class KnowledgeService {
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    public KnowledgeService(KnowledgeRepository knowledgeRepository){
        this.knowledgeRepository = knowledgeRepository;
    }

    public void init(){
        String sourcePath = "D:" + File.separator + "knowledge_points.csv";
        try {
            FileReader fr = new FileReader(sourcePath);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                Knowledge knowledge = new Knowledge();
                knowledge.setContent(str);
                knowledgeRepository.save(knowledge);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
