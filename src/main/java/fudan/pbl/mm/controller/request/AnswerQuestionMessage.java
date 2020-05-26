package fudan.pbl.mm.controller.request;

public class AnswerQuestionMessage {
    private Long questionId;
    private String answer;
    private Long userId;

    public AnswerQuestionMessage(){}

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
