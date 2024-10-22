package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject{

    //спрайт
    public Bitmap bmp;
    public Sprite sprite;

    //конструктор
    public Player(int x, int y, Bitmap bmp) {
        this.bmp = bmp;                    //возвращаем рисунок
        this.x = x;                        //отступ по х нет
        this.y = y; //делаем по центру
        sprite = new Sprite(bmp, x, y,4);
    }

    //рисуем наш спрайт
    public void onDraw(Canvas c, int x, int y) {
        sprite.startAnimation(c, x, y, 100, 1, () -> {});
    }
}
