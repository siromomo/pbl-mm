package fudan.pbl.mm.controller.request;

public class PackKnowledgeMessage {
    private Long packId;
    private Long knowledgeId;
    public PackKnowledgeMessage(){}

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public Long getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
}
