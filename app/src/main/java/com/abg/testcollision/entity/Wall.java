package com.abg.testcollision.entity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.abg.testcollision.R;
import com.abg.testcollision.gamemode.GameMode;

import java.util.ArrayList;
import java.util.List;

public class Wall extends GameObject {

    //спрайт
    public List<Bitmap> bmp = new ArrayList<>();

    public int hp = 3;

    //конструктор
    public Wall(Resources res, int x, int y) {
        bmp.add(BitmapFactory.decodeResource(res, R.drawable.wall_0));
        bmp.add(BitmapFactory.decodeResource(res, R.drawable.wall_1));
        bmp.add(BitmapFactory.decodeResource(res, R.drawable.wall_2));
        this.x = x + 10;
        this.y = y - 10;
        this.width = bmp.get(0).getWidth() + 10;
        this.height = bmp.get(0).getHeight() - 10;
    }

    //рисуем наш спрайт
    public void onDraw(Canvas c) {
        if (hp == 3) {
            c.drawBitmap(bmp.get(0), x, y, null);
        } else if (hp == 2) {
            c.drawBitmap(bmp.get(1), x, y, null);
        }  else if (hp == 1) {
            c.drawBitmap(bmp.get(2), x, y, null);
        }

    }

    public void damage(Destroy destroy) {
        if (hp > 0) {
            hp--;
        } else {
            destroy.onDestroy();
        }
    }
}
