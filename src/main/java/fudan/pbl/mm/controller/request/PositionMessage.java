package fudan.pbl.mm.controller.request;

public class PositionMessage {
    private int x;
    private int y;
    private int z;
    private float rotation;
    private long objectId;

    public PositionMessage(){}
    public PositionMessage(int x, int y, int z, long objectId){
        this.x = x;
        this.y = y;
        this.z = z;
        this.objectId = objectId;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
