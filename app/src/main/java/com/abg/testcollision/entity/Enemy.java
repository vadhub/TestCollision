package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.abg.testcollision.gamemode.GameViewDefense;

import java.util.Random;

public class Enemy extends GameObject {

    public GameViewDefense gameView;
    public Bitmap bmp;
    public boolean forward = true;
    public int distance;

    /**Конструктор класса*/
    public Enemy(GameViewDefense gameView, Bitmap bmp, int x, int y){
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        this.x = x;
        this.y = rnd.nextInt(y);
        this.speed = rnd.nextInt(10) + 3;

        this.width = 32;
        this.height = 32;
    }

    public void update(){
        if (forward) {
            x -= speed;
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

    public void onDraw(Canvas c){
        update();
        c.drawBitmap(bmp, x, y, null);
    }
}
