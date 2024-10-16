package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.abg.testcollision.gamemode.GameMode;
import com.abg.testcollision.gamemode.GameViewDefense;

import java.util.Random;

public class Enemy extends GameObject {

    public GameMode gameView;
    public Bitmap bmp;
    public boolean forward = true;
    public int distance;
    public boolean vector;

    /**Конструктор класса*/
    public Enemy(GameMode gameView, Bitmap bmp, int x, int y){
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        this.x = x;
        this.y = rnd.nextInt(y);
        this.speed = rnd.nextInt(10) + 3;

        this.width = 32;
        this.height = 32;
    }

    /**
    * @param vector true - from down to up, false - from right to left
    * */
    public Enemy(GameMode gameView, Bitmap bmp, int x, int y, int speed, boolean vector){
        this.gameView = gameView;
        this.bmp = bmp;

        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = 32;
        this.height = 32;
        this.vector = vector;
    }

    public void update(){
        if (forward) {
            x -= speed;
        } else {
            backMove();
        }
    }

    public void updateToUp(){
        if (forward) {
            y -= speed;
        } else {
            backMove();
        }
    }

    public void backMove() {
        if (distance != 10) {
            x += 2;
            distance += 2;
        } else {
            forward = true;
            distance = 0;
        }
    }

    public void backDownMove() {
        if (distance != 10) {
            y += 2;
            distance += 2;
        } else {
            forward = true;
            distance = 0;
        }
    }

    public void onDraw(Canvas c){
        if (!vector) {
            update();
        } else {
            updateToUp();
        }
        c.drawBitmap(bmp, x, y, null);
    }
}
