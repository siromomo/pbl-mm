package fudan.pbl.mm.service;

import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.ChoiceQuestion;
import fudan.pbl.mm.repository.ChoiceQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.FileHandler;

@Service
public class ChoiceQuestionService {
    private ChoiceQuestionRepository choiceQuestionRepository;

    @Autowired
    public ChoiceQuestionService(ChoiceQuestionRepository choiceQuestionRepository){
        this.choiceQuestionRepository = choiceQuestionRepository;
    }

    public ResponseObject addQuestion(Set<String> keywords, String stem,
                                              String choiceA, String choiceB,
                                              String choiceC, String choiceD, String correctChoice){
        StringBuilder builder = new StringBuilder();
        for(String key : keywords){
            builder.append(key);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        ChoiceQuestion question = new ChoiceQuestion(builder.toString(), stem,
                choiceA, choiceB, choiceC, choiceD, correctChoice);
        choiceQuestionRepository.save(question);
        return new ResponseObject<>(200, "success", null);
    }

    public ResponseObject<ChoiceQuestion> getRandomQuestion(){
        return new ResponseObject<>(200, "success", choiceQuestionRepository.findRandomQuestion());
    }

    void read(){
        File file = new File("D://ques.txt");
        Set<String> keywords = new HashSet<>();
        keywords.add("免疫调节");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String str;
            while((str = reader.readLine()) != null){
                builder.append(str);
            }
            String content = builder.toString();
            String[] questions = content.split("[0-9]{1,2}．");
            for(String q : questions){
                String[] temp = q.split("[A-D]．");
                if(temp.length <= 1) continue;
                String stem = temp[0].replaceAll("\\(.*?\\)", "");
                System.out.println(stem);
                String choiceA = temp[1];
                System.out.println(choiceA);
                String choiceB = temp[2];
                System.out.println(choiceB);
                String choiceC = temp[3];
                System.out.println(choiceC);
                String choiceD = temp[4].split("解析")[0];
                System.out.println(choiceD);
                String correct = temp[4].split("答案：")[1];
                System.out.println(correct);
                addQuestion(keywords, stem, choiceA, choiceB, choiceC, choiceD, correct);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
