package fudan.pbl.mm.controller.request;

public class ChatMessage {
    private long toId;
    private long fromId;
    private String content;

    public ChatMessage() {}
    public ChatMessage(long toId, long fromId, String content){
        this.toId = toId;
        this.fromId = fromId;
        this.content = content;
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }
}
