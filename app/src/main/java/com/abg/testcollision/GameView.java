package com.abg.testcollision;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.abg.testcollision.entity.Bullet;
import com.abg.testcollision.entity.Enemy;
import com.abg.testcollision.entity.Player;
import com.abg.testcollision.entity.Wall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    /**
     * Объект класса GameLoopThread
     */
    private GameThread mThread;

    private State state = State.BUILD;

    public int shotX;
    public int shotY;

    private List<Bullet> ball = new ArrayList<>();
    private Player player;
    Bitmap players;

    private List<Enemy> enemy = new ArrayList<>();
    Bitmap enemies;

    private List<Wall> walls= new ArrayList<>();
    Bitmap wall;

    private Thread threadEnemy = new Thread(this);

    /**
     * Переменная запускающая поток рисования
     */
    private boolean running = false;

    public GameView(Context context, AttributeSet attrs) {
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

        players = BitmapFactory.decodeResource(getResources(), R.drawable.part);
        player = new Player(this, players);

        enemies = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        wall = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
    }

    @Override
    public void run() {
        while (true) {
            Random rnd = new Random();
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
        private GameView view;

        /**
         * Конструктор класса
         */
        public GameThread(GameView view) {
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
                    synchronized (view.getHolder()) {
                        // собственно рисование
                        onDraw(canvas);
                        testCollision();
                        testCollisionWallWithEnemy();
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


    /**
     * Функция рисующая все спрайты и фон
     */
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(player.bmp, 5.0f, 500.0f, null);

        Iterator<Enemy> i = enemy.iterator();
        while(i.hasNext()) {
            Enemy e = i.next();
            if(e.x >= 1000 || e.x <= 1000) {
                e.onDraw(canvas);
            } else {
                i.remove();
            }
        }

        Iterator<Wall> w = walls.iterator();
        while(w.hasNext()) {
            Wall e = w.next();
            if(e.hp > 0) {
                e.onDraw(canvas);
            } else {
                w.remove();
            }
        }

        Iterator<Bullet> j = ball.iterator();
        while (j.hasNext()) {
            Bullet b = j.next();
            if (b.x >= 1000 || b.x <= 1000) {
                b.onDraw(canvas);
            } else {
                j.remove();
            }
        }
    }

    /*Проверка на столкновения*/
    private void testCollision() {
        Iterator<Bullet> b = ball.iterator();
        while(b.hasNext()) {
            Bullet balls = b.next();
            Iterator<Enemy> i = enemy.iterator();
            while(i.hasNext()) {
                Enemy enemies = i.next();

                if ((Math.abs(balls.x - enemies.x) <= (balls.width + enemies.width) / 2f)
                        && (Math.abs(balls.y - enemies.y) <= (balls.height + enemies.height) / 2f)) {
                    i.remove();
                    b.remove();
                }
            }
        }
    }

    private void testCollisionWallWithEnemy() {
        Iterator<Wall> wall = walls.iterator();
        while(wall.hasNext()) {
            Wall w = wall.next();
            Iterator<Enemy> i = enemy.iterator();
            while(i.hasNext()) {
                Enemy e = i.next();
                if (Physic.checkCollision(w, e)) {
                    e.speed = 0;
                    //wall.remove();
                }
            }
        }
    }

    public Bullet createSpriteBullet(int resource, float angleCorrect) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Bullet(this, bmp, player.x, getHeight() / 2, angleCorrect);
    }

    public boolean onTouchEvent(MotionEvent e) {
        shotX = (int) e.getX();
        shotY = (int) e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (state == State.SHOOT) {
                ball.add(createSpriteBullet(R.drawable.coin, 0));
                ball.add(createSpriteBullet(R.drawable.coin, 0.2f));
                ball.add(createSpriteBullet(R.drawable.coin, -0.2f));
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
