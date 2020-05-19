package fudan.pbl.mm.controller.request;

public class PackMessage {
    private Long packId;
    private String cellInfoType;

    public PackMessage(){}

    public Long getPackId() {
        return packId;
    }

    public String getCellInfoType() {
        return cellInfoType;
    }

    public void setCellInfoType(String cellInfoType) {
        this.cellInfoType = cellInfoType;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }
}
