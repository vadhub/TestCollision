package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.abg.testcollision.gamemode.GameMode;
import com.abg.testcollision.gamemode.GameModeDefense;

import java.util.Random;

public class Enemy extends GameObject {

    public GameMode gameView;
    public Bitmap bmp;
    public boolean forward = true;
    public int distance;
    public boolean vector;
    protected Sprite sprite;
    protected int hp;
    protected Area area;

    /**
     * Конструктор класса
     */
    public Enemy(GameMode gameView, Bitmap bmp, int speed, int x, int y, int hp) {
        this.gameView = gameView;
        this.bmp = bmp;

        this.x = x;
        Random rnd = new Random();
        this.y = rnd.nextInt(y);
        this.speed =  speed;

        this.width = 32;
        this.height = 32;

        this.hp = hp;

        sprite = new Sprite(bmp, 0, 0, 2);
    }

    /**
     * @param vector true - from down to up, false - from right to left
     */
    public Enemy(GameMode gameView, Bitmap bmp, int x, int y, int speed, boolean vector) {
        this.gameView = gameView;
        this.bmp = bmp;

        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = 32;
        this.height = 32;
        this.vector = vector;
    }

    public Enemy(GameMode gameView, Bitmap bmp, int speed, int x, Area area, int hp) {
        this.gameView = gameView;
        this.bmp = bmp;

        this.x = x;
        this.y = (int) (area.getX2() + (Math.random() * (area.getX2() - area.getX1())));
        this.speed = speed;
        this.width = 32;
        this.height = 32;
        this.hp = hp;
        sprite = new Sprite(bmp, 0, 0, 2);
    }

    public Enemy() {
    }

    public void damage(Destroy destroy) {
        if (hp > 0) {
            hp--;
        } else {
            destroy.onDestroy();
        }
    }


    public void update() {
        if (forward) {
            x -= speed;
        } else {
            backMove();
        }
    }

    public void updateToUp() {
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

    public void onDraw(Canvas c) {

        c.drawBitmap(bmp, x, y, null);

        if (!vector) {
            update();
        } else {
            updateToUp();
        }
    }

    public void onDrawSprites(Canvas c) {

        sprite.startAnimation(c, x, y, 150, 20, () -> {});

        if (!vector) {
            update();
        } else {
            updateToUp();
        }
    }

}
