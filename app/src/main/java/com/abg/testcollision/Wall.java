package com.abg.testcollision;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Wall {
    GameView gameView;

    //спрайт
    Bitmap bmp;

    int hp = 3;

    //х и у координаты рисунка
    int x;
    int y;

    int width = 32;
    int height = 64;

    //конструктор
    public Wall(GameView gameView, Bitmap bmp, int x, int y) {
        this.gameView = gameView;
        this.bmp = bmp;                    //возвращаем рисунок
        this.x = x;                        //отступ по х нет
        this.y = y; //делаем по центру
    }

    //рисуем наш спрайт
    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, x, y, null);
    }
}
