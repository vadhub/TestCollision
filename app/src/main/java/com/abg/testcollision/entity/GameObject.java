package com.abg.testcollision.entity;

public class GameObject {
    public int x;
    public int y;
    public int width;
    public  int height;
    public int speed;

    public interface Destroy {
        void onDestroy();
    }
}
