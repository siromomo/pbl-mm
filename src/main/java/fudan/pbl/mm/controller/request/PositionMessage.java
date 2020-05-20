package fudan.pbl.mm.controller.request;

public class PositionMessage {
    private float x;
    private float y;
    private float z;
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
