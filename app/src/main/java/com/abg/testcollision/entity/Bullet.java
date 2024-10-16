package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.abg.testcollision.gamemode.GameMode;
import com.abg.testcollision.gamemode.GameViewDefense;

public class Bullet extends GameObject{
    /**Картинка*/
    private Bitmap bmp;

    public double angle;

    public GameMode gameView;

    /**Конструктор*/
    public Bullet(GameMode gameView, Bitmap bmp, int x, int y, float angleCorrect) {
        this.gameView=gameView;
        this.bmp=bmp;
        speed = 25;

        this.x = x;            //позиция по Х
        this.y = y;          //позиция по У
        this.width = 27;       //ширина снаряда
        this.height = 40;      //высота снаряда

        //угол полета пули в зависипости от координаты косания к экрану
        angle = Math.atan2(gameView.shotY - y, gameView.shotX - x) + angleCorrect;
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
