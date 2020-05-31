package fudan.pbl.mm.service;

import com.google.common.collect.Lists;
import fudan.pbl.mm.controller.request.auth.KnowledgeRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Knowledge;
import fudan.pbl.mm.repository.KnowledgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

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

    public ResponseObject<Knowledge> addKnowledge(KnowledgeRequest request){
        Knowledge knowledge = new Knowledge();
        knowledge.setContent(request.getContent());
        knowledge.setType(request.getType());
        knowledgeRepository.save(knowledge);
        return new ResponseObject<>(200, "success", knowledge);
    }

    public ResponseObject<Knowledge> modifyKnowledge(KnowledgeRequest request){
        Long id = request.getId();
        String content = request.getContent();
        int type = request.getType();
        Knowledge knowledge = knowledgeRepository.findKnowledgeById(id);
        if(knowledge == null){
            return new ResponseObject<>(404, "knowledge doesn't exist", null);
        }
        knowledge.setContent(content);
        knowledge.setType(type);
        knowledgeRepository.save(knowledge);
        return new ResponseObject<>(200, "success", knowledge);
    }

    public ResponseObject<Knowledge> deleteKnowledge(Long id){
        Knowledge knowledge = knowledgeRepository.findKnowledgeById(id);
        if(knowledge == null){
            return new ResponseObject<>(404, "knowledge doesn't exist", null);
        }
        knowledgeRepository.delete(knowledge);
        return new ResponseObject<>(200, "success", knowledge);
    }

    public ResponseObject<List<Knowledge>> getKnowledgeList(){
        return new ResponseObject<>(200, "success",
                Lists.newArrayList(knowledgeRepository.findAll()));
    }
}
