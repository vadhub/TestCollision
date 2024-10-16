package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.abg.testcollision.gamemode.GameMode;

public class Player extends GameObject{
    GameMode gameView;

    //спрайт
    public Bitmap bmp;

    //конструктор
    public Player(GameMode gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;                    //возвращаем рисунок
        this.x = 5;                        //отступ по х нет
        this.y = gameView.getHeight() / 2; //делаем по центру
    }

    //рисуем наш спрайт
    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, x, y, null);
    }
}
