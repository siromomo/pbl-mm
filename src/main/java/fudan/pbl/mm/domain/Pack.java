package fudan.pbl.mm.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CellInfo> cellInfoSet;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ChoiceQuestion> choiceQuestionSet;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Knowledge> knowledgeSet;
    @OneToMany
    @JsonIgnore
    private Set<Cell> cells;
    @Column(columnDefinition = "int(11) default 100")
    private int hp;
    private final static int DROP_NUM = 10;
    private final static int FILLED_NUM = 5;

    public Pack(){}

    public void setCells(Set<Cell> cell) {
        this.cells = cell;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void addToCells(Cell cell){
        if(this.cells == null) this.cells = new HashSet<>();
        this.cells.add(cell);
    }

    public Set<CellInfo> getCellInfoSet() {
        return cellInfoSet;
    }

    public void setCellInfoSet(Set<CellInfo> cellInfoSet) {
        this.cellInfoSet = cellInfoSet;
    }
    public void addToCellInfoSet(CellInfo cellInfo){
        if(cellInfoSet == null) cellInfoSet = new HashSet<>();
        cellInfoSet.add(cellInfo);
    }

    public Set<ChoiceQuestion> getChoiceQuestionSet() {
        return choiceQuestionSet;
    }

    public void setChoiceQuestionSet(Set<ChoiceQuestion> choiceQuestionSet) {
        this.choiceQuestionSet = choiceQuestionSet;
    }
    public void addToChoiceQuestionSet(ChoiceQuestion choiceQuestion){
        if(choiceQuestionSet == null) choiceQuestionSet = new HashSet<>();
        choiceQuestionSet.add(choiceQuestion);
    }

    public Set<Knowledge> getKnowledgeSet() {
        return knowledgeSet;
    }

    public void setKnowledgeSet(Set<Knowledge> knowledgeSet) {
        this.knowledgeSet = knowledgeSet;
    }
    public void addToKnowledgeSet(Knowledge knowledge){
        if(knowledgeSet == null) knowledgeSet = new HashSet<>();
        knowledgeSet.add(knowledge);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
    public void dropHp(){
        hp -= DROP_NUM;
    }

    public Long getId() {
        return id;
    }

    public boolean isFilled(){
        if(cellInfoSet == null || knowledgeSet == null || choiceQuestionSet == null)
            return false;
        int total = cellInfoSet.size() + knowledgeSet.size() + choiceQuestionSet.size();
        return total >= FILLED_NUM;
    }
}
