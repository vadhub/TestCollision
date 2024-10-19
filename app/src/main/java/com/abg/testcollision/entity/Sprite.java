package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private Bitmap bitmap;
    private int widthFrame;
    private int height;
    private int currentFrame = 0;
    private int frames;

    public Sprite(Bitmap bitmap, int frames) {
        this.bitmap = bitmap;
        this.height = bitmap.getHeight();
        this.frames = frames;

        widthFrame = bitmap.getWidth() / frames;
    }

    public void startAnimation(Canvas canvas, int x, int y) {

        int srcX = currentFrame * widthFrame;
        int srcY = height;

        if (currentFrame < frames) {
            currentFrame++;
        } else {
            currentFrame = 0;
        }
        Rect src = new Rect(srcX, 0, srcX+widthFrame, srcY);
        Rect dst = new Rect(x, y, x + widthFrame, y + srcY);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

}
