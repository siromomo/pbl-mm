package fudan.pbl.mm.controller.response;

public class ResponseObject<T> {
    private int code;
    private String message;
    private T content;

    public ResponseObject() {}
    public ResponseObject(int code, String message, T content){
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
