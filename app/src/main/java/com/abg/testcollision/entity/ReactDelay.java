package com.abg.testcollision.entity;

public class ReactDelay {
    private long prevTime;

    public void delay(long delay, Listener listener) {
        long newTime = System.currentTimeMillis();
        if ((newTime - prevTime) >= delay) {
            listener.react();
            prevTime = newTime;
        }
    }
}
