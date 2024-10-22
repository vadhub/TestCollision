package com.abg.testcollision.entity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.abg.testcollision.gamemode.GameMode;

public class Bullet extends GameObject {
    /**Картинка*/
    private Bitmap bmp;

    public double angle;
    private int damage;
    private Matrix matrix = new Matrix();

    public GameMode gameView;

    /**Конструктор*/
    public Bullet(GameMode gameView, Bitmap bmp, int x, int y, float angleCorrect, int damage) {
        this.gameView=gameView;
        speed = 25;

        this.x = x;            //позиция по Х
        this.y = y;          //позиция по У
        this.width = 27;       //ширина снаряда
        this.height = 40;      //высота снаряда
        angle = Math.atan2(gameView.yClick - y, gameView.xClick - x) + angleCorrect;

        float a = (float) (Math.atan2(gameView.yClick - y, gameView.xClick - x) * 180 / Math.PI) + 90;
        Log.d("!!!", gameView.xClick+" "+gameView.yClick + " | " + x +" " + y + " " + a);
        matrix.setRotate(a);
        this.bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getHeight(), bmp.getWidth(), matrix,true);
    }


    /**Перемещение объекта, его направление*/
    private void update() {
        x += speed * Math.cos(angle);         //движение по Х со скоростью mSpeed и углу заданном координатой angle
        y += speed * Math.sin(angle);         // движение по У -//-
    }

    /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas) {
        update();                              //говорим что эту функцию нам нужно вызывать для работы класса
        canvas.drawBitmap(bmp, x, y, null);
    }
}
