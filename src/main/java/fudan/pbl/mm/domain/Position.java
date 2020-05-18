package fudan.pbl.mm.domain;

import java.util.Calendar;
import java.util.Date;

public class Position {
    private int x;
    private int y;
    private int z;
    private float rotation;
    private int lastRand;

    private static final int POSITION_BOUND = 100;
    private static final float ROTATION_BOUND = 360;

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
        rotation = (float)(ROTATION_BOUND * Math.random());
    }

    public void randomUpdate(){
        int rand = (int)(3 * Math.random());
        if(rand == lastRand){
            rand = (rand + 1) % 3;
        }
        Calendar calendar = Calendar.getInstance();
        int timeFlag = calendar.get(Calendar.SECOND) <= 30 ? 1 : -1;
        switch (rand){
            case 0: x += timeFlag * POSITION_BOUND / 100; break;
            case 1: y += timeFlag * POSITION_BOUND / 100; break;
            case 2: z += timeFlag * POSITION_BOUND / 100; break;
        }
        lastRand = rand;
        checkValid();
    }

    private void checkValid(){
        if(x < 0) x = 0;
        if(x > POSITION_BOUND) x = POSITION_BOUND;
        if(y < 0) y = 0;
        if(y > POSITION_BOUND) y = POSITION_BOUND;
        if(z < 0) z = 0;
        if(z > POSITION_BOUND) z = POSITION_BOUND;
    }

    public double calculateDistance(Position p1){
        return Math.pow(Math.pow((p1.x - x),2) + Math.pow((p1.y - y), 2) + Math.pow((p1.z - z), 2), 0.5);
    }

    public double calculateDistance2d(Position p1){
        return Math.pow(Math.pow((p1.x - x),2) + Math.pow((p1.y - y), 2), 0.5);
    }


    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}
