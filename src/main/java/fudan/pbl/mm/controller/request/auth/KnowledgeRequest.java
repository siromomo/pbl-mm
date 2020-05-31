package fudan.pbl.mm.controller.request.auth;

public class KnowledgeRequest {
    private Long id;
    private int type;
    private String content;

    public KnowledgeRequest(){}

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
