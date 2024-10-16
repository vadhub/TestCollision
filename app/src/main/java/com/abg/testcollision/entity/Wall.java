package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.abg.testcollision.gamemode.GameViewDefense;

public class Wall extends GameObject {
    GameViewDefense gameView;

    //спрайт
    public Bitmap bmp;

    public int hp = 3;

    //конструктор
    public Wall(GameViewDefense gameView, Bitmap bmp, int x, int y) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 64;
    }

    //рисуем наш спрайт
    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, x, y, null);
    }

    public void destroyWall() {
        hp--;
    }
}
