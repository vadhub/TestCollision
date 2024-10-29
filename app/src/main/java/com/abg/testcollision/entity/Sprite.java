package com.abg.testcollision.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private final Bitmap bitmap;
    private final int widthFrame;
    private final int height;
    private int currentFrame = 0;
    private final int frames;
    private final ReactDelay delayFrame;
    private final int x;
    private final int y;

    public Sprite(Bitmap bitmap, int x, int y, int frames) {
        this.bitmap = bitmap;
        this.height = bitmap.getHeight();
        this.frames = frames;
        this.x = x;
        this.y = y;

        widthFrame = bitmap.getWidth() / frames;
        delayFrame = new ReactDelay();
    }

    public void startAnimation(Canvas canvas, Listener listener) {

        int srcX = currentFrame * widthFrame;
        int srcY = height;
        if (currentFrame < frames) {
            delayFrame.delay(50, () -> currentFrame++);
        } else {
            listener.react();
            currentFrame = 0;
        }

        Rect src = new Rect(srcX, 0, srcX+widthFrame, srcY);
        Rect dst = new Rect(x, y, x + widthFrame, y + srcY);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public void startAnimation(Canvas canvas, int x, int y, int fps, int scale, Listener listener) {

        int srcX = currentFrame * widthFrame;
        int srcY = height;
        if (currentFrame < frames-1) {
            delayFrame.delay(fps, () -> currentFrame++);
        } else {
            listener.react();
            currentFrame = 0;
        }

        Rect src = new Rect(srcX, 0, srcX+widthFrame, srcY);
        Rect dst = new Rect(x, y, x + widthFrame + scale, y + srcY + scale);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public void startAnimation(Canvas canvas, int x, int y, int fps, int scale, boolean isStartAnimation, Listener stopAnimationListener) {

        int srcX = currentFrame * widthFrame;
        int srcY = height;
        if (isStartAnimation) {
            if (currentFrame < frames - 1) {
                delayFrame.delay(fps, () -> currentFrame++);
            } else {
                stopAnimationListener.react();
                currentFrame = 0;
            }
        }

        Rect src = new Rect(srcX, 0, srcX + widthFrame, srcY);
        Rect dst = new Rect(x, y, x + widthFrame + scale, y + srcY + scale);
        canvas.drawBitmap(bitmap, src, dst, null);
    }
}
