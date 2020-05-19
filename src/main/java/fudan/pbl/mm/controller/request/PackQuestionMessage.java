package fudan.pbl.mm.controller.request;

public class PackQuestionMessage {
    private Long packId;
    private Long questionId;

    public PackQuestionMessage(){}

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public Long getPackId() {
        return packId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
