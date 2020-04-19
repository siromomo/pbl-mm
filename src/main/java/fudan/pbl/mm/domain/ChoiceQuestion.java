package fudan.pbl.mm.domain;

import javax.persistence.*;

@Entity
public class ChoiceQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String keyword;
    @Column(unique = true)
    private String stem;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String correctChoice;

    public ChoiceQuestion(){}
    public ChoiceQuestion(String keyword, String stem, String choiceA,
                          String choiceB, String choiceC, String choiceD, String correctChoice){
        this.keyword = keyword;
        this.stem = stem;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.correctChoice = correctChoice;
    }

    public Long getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public String getStem() {
        return stem;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }
}
