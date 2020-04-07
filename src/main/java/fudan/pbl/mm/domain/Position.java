package fudan.pbl.mm.domain;

public class Position {
    private int x;
    private int y;
    private int z;

    private static final int POSITION_BOUND = 100;

    public Position() {
        setRandomPosition();
    }
    public Position(boolean random){
        if(random) setRandomPosition();
    }
    public Position(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setRandomPosition(){
        x = (int)(POSITION_BOUND * Math.random());
        y = (int)(POSITION_BOUND * Math.random());
        z = (int)(POSITION_BOUND * Math.random());
    }

    public double calculateDistance(Position p1){
        return Math.pow(Math.pow((p1.x - x),2) + Math.pow((p1.y - y), 2) + Math.pow((p1.z - z), 2), 0.5);
    }
}
