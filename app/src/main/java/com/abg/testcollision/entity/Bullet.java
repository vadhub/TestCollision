package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.abg.testcollision.GameView;

public class Bullet extends GameObject{
    /**Картинка*/
    private Bitmap bmp;

    /**Скорость по Х=15*/
    private int mSpeed=25;

    public double angle;

    public GameView gameView;

    /**Конструктор*/
    public Bullet(GameView gameView, Bitmap bmp, int x, int y, float angleCorrect) {
        this.gameView=gameView;
        this.bmp=bmp;

        this.x = x;            //позиция по Х
        this.y = y;          //позиция по У
        this.width = 27;       //ширина снаряда
        this.height = 40;      //высота снаряда

        //угол полета пули в зависипости от координаты косания к экрану
        angle = Math.atan((double)(y - gameView.shotY) / (x - gameView.shotX)) + angleCorrect;
    }

    /**Перемещение объекта, его направление*/
    private void update() {
        x += mSpeed * Math.cos(angle);         //движение по Х со скоростью mSpeed и углу заданном координатой angle
        y += mSpeed * Math.sin(angle);         // движение по У -//-
    }

    /**Рисуем наши спрайты*/
    public void onDraw(Canvas canvas) {
        update();                              //говорим что эту функцию нам нужно вызывать для работы класса
        canvas.drawBitmap(bmp, x, y, null);
    }
}
