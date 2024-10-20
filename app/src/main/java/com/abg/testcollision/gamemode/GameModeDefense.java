package com.abg.testcollision.gamemode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.abg.testcollision.R;
import com.abg.testcollision.entity.Bullet;
import com.abg.testcollision.entity.Enemy;
import com.abg.testcollision.entity.Player;
import com.abg.testcollision.entity.Sprite;
import com.abg.testcollision.entity.Wall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameModeDefense extends GameMode implements Runnable {

    /**
     * Объект класса GameLoopThread
     */
    private GameThread mThread;
    private int countPassEnemy;

    private State state = State.BUILD;

    private List<Bullet> ball = new ArrayList<>();
    private Player player;
    Bitmap players;

    private List<Sprite> explosions = new ArrayList<>();
    Bitmap explosion;

    private List<Enemy> enemy = new ArrayList<>();
    Bitmap enemies;

    private List<Wall> walls = new ArrayList<>();
    Bitmap wall;

    private Thread threadEnemy = new Thread(this);
    private ChangeCountListener changeCountListener;
    Random rnd = new Random();

    public void setChangeCountListener(ChangeCountListener changeCountListener) {
        this.changeCountListener = changeCountListener;
    }

    /**
     * Переменная запускающая поток рисования
     */
    private boolean running = false;

    public GameModeDefense(Context context, AttributeSet attrs) {
        super(context, attrs);

        mThread = new GameThread(this);
        threadEnemy.start();

        /*Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback() {
            /*** Уничтожение области рисования */
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                mThread.setRunning(false);
                while (retry) {
                    try {
                        // ожидание завершение потока
                        mThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            /** Создание области рисования */
            public void surfaceCreated(SurfaceHolder holder) {
                mThread.setRunning(true);
                mThread.start();
            }

            /** Изменение области рисования */
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        players = BitmapFactory.decodeResource(getResources(), R.drawable.player, options);

        player = new Player((getWidth() - 32) / 2, getHeight() - 100, players);
        enemies = BitmapFactory.decodeResource(getResources(), R.drawable.truk);
        wall = BitmapFactory.decodeResource(getResources(), R.drawable.brick);

        explosion = BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(rnd.nextInt(2000));
                enemy.add(new Enemy(this, enemies, 1580, 720));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------Start of GameThread--------------------------------------------------\\

    public class GameThread extends Thread {

        /**
         * Объект класса
         */
        private GameMode view;

        /**
         * Конструктор класса
         */
        public GameThread(GameMode view) {
            this.view = view;
        }

        /**
         * Задание состояния потока
         */
        public void setRunning(boolean run) {
            running = run;
        }

        /**
         * Действия, выполняемые в потоке
         */
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    // подготовка Canvas-а
                    canvas = view.getHolder().lockCanvas();
                    canvas.drawColor(Color.WHITE);
                    synchronized (view.getHolder()) {
                        // собственно рисование
                        onDraw(canvas);
                        testCollision();
                        testCollisionWallWithEnemy();
                        testCollisionWithBound();
                        testCollisionWithBoundTop();
                    }
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    //-------------End of GameThread--------------------------------------------------\\


    protected void onDraw(Canvas canvas) {
        player.sprite.startAnimation(canvas, () -> {});

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

    /*Проверка на столкновения*/
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
                    e.speed = -2;
                    e.forward = false;
                }
            }
        }
    }

    private void testCollisionWithBound() {
        Iterator<Enemy> i = enemy.iterator();
        while (i.hasNext()) {
            Enemy enemies = i.next();
            if (enemies.x < 0) {
                i.remove();
                countPassEnemy = countPassEnemy+1;
                changeCountListener.change(countPassEnemy);
            }
        }
    }

    private void testCollisionWithBoundTop() {
        ball.removeIf(bullet -> bullet.y < 0);
    }

    public Bullet createSpriteBullet(int resource, float angleCorrect) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Bullet(this, bmp, getWidth() / 2, getHeight() - 60, angleCorrect,1);
    }

    public boolean onTouchEvent(MotionEvent e) {
        xClick = (int) e.getX();
        yClick = (int) e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (state == State.SHOOT) {
                ball.add(createSpriteBullet(R.drawable.bullet, (float) rnd.nextInt(2) / 10));
                ball.add(createSpriteBullet(R.drawable.bullet, (float) rnd.nextInt(2) / 10));
                ball.add(createSpriteBullet(R.drawable.bullet, (float) rnd.nextInt(2) * -1 / 10));
            } else {
                walls.add(new Wall(this, wall, (int) e.getX(), (int) e.getY()));
            }
        }
        return true;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        BUILD, SHOOT
    }
}
