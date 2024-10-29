package com.abg.testcollision.gamemode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.abg.testcollision.R;
import com.abg.testcollision.entity.Bullet;
import com.abg.testcollision.entity.Enemy;
import com.abg.testcollision.entity.Sprite;
import com.abg.testcollision.entity.Wall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameModeAssault extends GameMode implements Runnable {

    private GameThread mThread;
    private int countPassEnemy;

    private List<Bullet> ball = new ArrayList<>();
    private List<Sprite> explosions = new ArrayList<>();
    Bitmap explosion;

    private List<Enemy> enemy = new ArrayList<>();
    Bitmap players;

    private List<Wall> walls = new ArrayList<>();
    Bitmap wall;

    private ChangeAmountListener changeCountListener;
    Random rnd = new Random();

    private Thread threadShot = new Thread(this);

    public void setChangeCountListener(ChangeAmountListener changeCountListener) {
        this.changeCountListener = changeCountListener;
    }

    private boolean running = false;

    public GameModeAssault(Context context, AttributeSet attrs) {
        super(context, attrs);

        mThread = new GameThread(this);
        threadShot.start();

        getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                mThread.setRunning(false);
                while (retry) {
                    try {
                        mThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                mThread.setRunning(true);
                mThread.start();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });

        players = BitmapFactory.decodeResource(getResources(), R.drawable.canon_fire);
        wall = BitmapFactory.decodeResource(getResources(), R.drawable.brick_rotated);
        explosion = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(rnd.nextInt(2000));
                shot();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------Start of GameThread--------------------------------------------------\\

    public class GameThread extends Thread {
        private GameMode view;

        public GameThread(GameMode view) {
            this.view = view;
        }

        public void setRunning(boolean run) {
            running = run;
        }

        public void run() {
            Canvas canvas = null;
            generateMap();
            while (running) {
                try {
                    canvas = view.getHolder().lockCanvas();
                    canvas.drawColor(Color.WHITE);

                    synchronized (view.getHolder()) {
                        onDraw(canvas);
                        testCollision();
                        testCollisionWallWithEnemy();
                        testCollisionWithBound();
                        testCollisionWithBoundTop();
                    }
                } catch (Exception ignored) {
                } finally {
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    //-------------End of GameThread--------------------------------------------------\\


    protected void onDraw(Canvas canvas) {

        Iterator<Wall> w = walls.iterator();
        while (w.hasNext()) {
            Wall e = w.next();
            if (e.hp > 0) {
                e.onDraw(canvas);
            } else {
                w.remove();
            }
        }

        Iterator<Enemy> i = enemy.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            e.onDraw(canvas);
        }

        Iterator<Bullet> j = ball.iterator();
        while (j.hasNext()) {
            Bullet b = j.next();
            b.onDraw(canvas);
        }

        Iterator<Sprite> s = explosions.iterator();
        while (s.hasNext()) {
            Sprite sprite = s.next();
            sprite.startAnimation(canvas, s::remove);
        }
    }

    private void testCollision() {
        Iterator<Bullet> b = ball.iterator();
        while (b.hasNext()) {
            Bullet balls = b.next();
            Iterator<Enemy> i = enemy.iterator();
            while (i.hasNext()) {
                Enemy enemies = i.next();

                if ((Math.abs(balls.x - enemies.x) <= (balls.width + enemies.width))
                        && (Math.abs(balls.y - enemies.y) <= (balls.height + enemies.height))) {
                    explosions.add(new Sprite(explosion, balls.x, balls.y, 7));
                    i.remove();
                    b.remove();
                }
            }
        }
    }

    private void testCollisionWallWithEnemy() {
        Iterator<Wall> wall = walls.iterator();
        while (wall.hasNext()) {
            Wall w = wall.next();
            Iterator<Enemy> i = enemy.iterator();
            while (i.hasNext()) {
                Enemy e = i.next();
                if ((Math.abs(w.x - e.x) <= (w.width + e.width))
                        && (Math.abs(w.y - e.y) <= (w.height + e.height))) {
                    //e.speed = -2;
                    e.forward = false;
                    //wall.remove();
                }
            }
        }
    }

    private void testCollisionWithBound() {
        Iterator<Enemy> i = enemy.iterator();
        while (i.hasNext()) {
            Enemy enemies = i.next();
            if (enemies.y < 0) {
                i.remove();
                countPassEnemy = countPassEnemy + 1;
                changeCountListener.change(countPassEnemy);
            }
        }
    }

    private void testCollisionWithBoundTop() {
        ball.removeIf(bullet -> bullet.y > getHeight());
    }

    public Bullet createSpriteBullet(int resource, float angleCorrect) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Bullet(this, bmp, getWidth() / 2,60, angleCorrect, 1);
    }

    public void shot() {
        ball.add(createSpriteBullet(R.drawable.bullet, (float) rnd.nextInt(3) / 10));
        ball.add(createSpriteBullet(R.drawable.bullet, (float) rnd.nextInt(2) / 10));
        ball.add(createSpriteBullet(R.drawable.bullet, (float) rnd.nextInt(2) * -1 / 10));
    }

    public void generateMap() {
        Random random = new Random();
        walls.add(new Wall(getResources(), random.nextInt(getWidth()), getHeight() / 2));
        walls.add(new Wall(getResources(), random.nextInt(getWidth()), getHeight() / 2));
        walls.add(new Wall(getResources(), random.nextInt(getWidth()), getHeight() / 2));
    }

    public boolean onTouchEvent(MotionEvent e) {
        xClick = (int) e.getX();
        yClick = (int) e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            enemy.add(new Enemy(this, players, xClick, yClick, 6,true));
        }
        return true;
    }
}
