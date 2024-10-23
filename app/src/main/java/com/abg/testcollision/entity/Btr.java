package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.abg.testcollision.gamemode.GameMode;

import java.util.List;
import java.util.Random;

public class Btr extends Enemy {

    private Bitmap cannon;
    private long prevTime = System.currentTimeMillis();
    public interface ShotListener {
        void shot();
    }

    /**
     * Конструктор класса
     */
    public Btr(GameMode gameView, Bitmap bmp, Bitmap cannon, int speed, int x, int y, int hp) {
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        this.x = x;
        this.y = rnd.nextInt(y);
        this.speed =  speed;

        this.width = 32;
        this.height = 32;

        this.hp = hp;
        Matrix matrix = new Matrix();
        matrix.setRotate(-90);
        this.cannon = Bitmap.createBitmap(cannon, 0, 0, cannon.getWidth(), cannon.getHeight(), matrix, true);
        sprite = new Sprite(bmp, 0, 0, 2);
    }

    public void shot(ShotListener shotListener) {
        long newTime = System.currentTimeMillis();
        if ((newTime - prevTime) > 1000) {
            shotListener.shot();
            prevTime = newTime;
        }
    }

    public void onDrawSprites(Canvas c) {
        sprite.startAnimation(c, x, y, 150, 20, () -> {});
        c.drawBitmap(cannon, x+50, y+50, null);
        if (!vector) {
            update();
        } else {
            updateToUp();
        }
    }

}
