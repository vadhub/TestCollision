package com.abg.testcollision;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Enemy {
    /**Х и У коорданаты*/
    public int x;
    public int y;

    /**Скорость*/
    public int speed;

    /**Выосота и ширина спрайта*/
    public int width;
    public int height;

    public GameView gameView;
    public Bitmap bmp;

    /**Конструктор класса*/
    public Enemy(GameView gameView, Bitmap bmp, int x, int y){
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
        x -= speed;
    }

    public void onDraw(Canvas c){
        update();
        c.drawBitmap(bmp, x, y, null);
    }
}
