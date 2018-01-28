package com.tbehlman;

/**
 * Created by Trev on 1/14/18.
 */

public class Timer {
    protected long startTime;
    protected final double speed;

    public Timer(double speed) {
        this.speed = speed;
        reset();
    }

    public float getTime() {
        return (float) ( ( System.currentTimeMillis() - startTime ) / 1000.0 * speed );
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }
}
