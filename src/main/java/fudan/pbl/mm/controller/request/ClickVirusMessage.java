package fudan.pbl.mm.controller.request;

public class ClickVirusMessage {
    private String cellType;
    /**
     * nerve, red, platelet, skin, stem, T, B, phagocyte
     * */
    private Long virusId;
    private Long userId;

    public ClickVirusMessage(){}
    public Long getVirusId() {
        return virusId;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public void setVirusId(Long virusId) {
        this.virusId = virusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
