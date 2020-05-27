package fudan.pbl.mm.domain;

import java.util.Calendar;

public class Position {
    private float x;
    private float y;
    private float z;
    private float rotation;
    private float rotationY;
    private float rotationZ;
    private int lastRand;
    public static int[] map;
    private static final int POSITION_BOUND = 480;
    private static final int MINUS_BOUND = -240;
    private static final float ROTATION_BOUND = 360;
    private int count = 0;
    private double angle;
    public Position() {
        setRandomPosition();
    }
    public Position(boolean random){
        if(random) setRandomPosition();
    }
    public Position(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Position(float x, float y, float z, float rotation){
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setRandomPosition(){
        x = (float) (POSITION_BOUND * Math.random()) + MINUS_BOUND;
        y = (float) (POSITION_BOUND * Math.random()) + MINUS_BOUND;
        z = (float) (POSITION_BOUND * Math.random()) + MINUS_BOUND;
        rotation = (float)(ROTATION_BOUND * Math.random()) - 180;
        rotationY = (float)(ROTATION_BOUND * Math.random()) - 180;
        rotationZ = (float)(ROTATION_BOUND * Math.random()) - 180;
    }

    public void randomUpdate(){
        if(count == 0){
            angle = Math.random()*Math.PI;
            count = 5;
        }
        count--;

        int rand = (int)(3 * Math.random());
        if(rand == lastRand){
            rand = (rand + 1) % 3;
        }
        Calendar calendar = Calendar.getInstance();
        int timeFlag = calendar.get(Calendar.SECOND) <= 30 ? 1 : -1;
//        switch (rand){
//            case 0: x += timeFlag * POSITION_BOUND / 50; break;
//            case 1: y += timeFlag * POSITION_BOUND / 50; break;
//            case 2: z += timeFlag * POSITION_BOUND / 50; break;
//        }
        x += Math.sin(angle)*POSITION_BOUND / 50;
        z += Math.cos(angle)*POSITION_BOUND / 50;
        rotation = (rotation + ((rand-1) * 5)) % ROTATION_BOUND;
        rotationY = (rotationY + ((rand-1) * 5)) % ROTATION_BOUND;
        rotationZ = (rotationZ + ((rand-1) * 5)) % ROTATION_BOUND;
        lastRand = rand;
        checkValid();
        y = map[((int)x+250)*500+(int)z+250]*0.5f-16;
    }

    private void checkValid(){
        if(x < MINUS_BOUND) x = MINUS_BOUND;
        if(x > POSITION_BOUND + MINUS_BOUND) x = POSITION_BOUND + MINUS_BOUND;
        if(y < MINUS_BOUND) y = MINUS_BOUND;
        if(y > POSITION_BOUND + MINUS_BOUND) y = POSITION_BOUND + MINUS_BOUND;
        if(z < MINUS_BOUND) z = MINUS_BOUND;
        if(z > POSITION_BOUND + MINUS_BOUND) z = POSITION_BOUND + MINUS_BOUND;
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

    public float getRotationY() {
        return rotationY;
    }

    public float getRotationZ() {
        return rotationZ;
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }

    public void setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
    }
}
