package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {
    private final Bitmap bitmap;
    private final int widthFrame;
    private final int height;
    private int currentFrame = 0;
    private final int frames;
    private long prevTime = System.currentTimeMillis();
    private int x;
    private int y;

    public Sprite(Bitmap bitmap, int x, int y, int frames) {
        this.bitmap = bitmap;
        this.height = bitmap.getHeight();
        this.frames = frames;
        this.x = x;
        this.y = y;

        widthFrame = bitmap.getWidth() / frames;
    }

    public void startAnimation(Canvas canvas, StopAnimationListener listener) {

        int srcX = currentFrame * widthFrame;
        int srcY = height;
        long nextTime = System.currentTimeMillis();
        if (currentFrame < frames) {
            if ((nextTime - prevTime) >= 50) {
                currentFrame++;
                prevTime = nextTime;
                Log.d("$$$$", "ok");
            }
        } else {
            listener.stop();
            currentFrame = 0;
        }
        Rect src = new Rect(srcX, 0, srcX+widthFrame, srcY);
        Rect dst = new Rect(x, y, x + widthFrame, y + srcY);
        canvas.drawBitmap(bitmap, src, dst, null);
    }
}
