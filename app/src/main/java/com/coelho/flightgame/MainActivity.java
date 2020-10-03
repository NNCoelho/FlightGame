package com.coelho.flightgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Declaração de Todas as Variáveis e Elementos
    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView player;
    private ImageView ruby;
    private ImageView diamond;
    private ImageView skull;
    private ImageView bomb;

    ImageButton pauseLb;
    ImageView startLb;

    private FrameLayout frameLb;
    TextView tv_coins;

    private int frameHeight;
    private int playerSize;
    private int screenWidth;

    // Posições e Velocidades
    private int playerY;
    private int rubyX;
    private int rubyY;
    private int diamondX;
    private int diamondY;
    private int skullX;
    private int skullY;
    private int bombX;
    private int bombY;

    private SoundEffects sound;

    private int playerSpeed;
    private int rubySpeed;
    private int diamondSpeed;
    private int skullSpeed;
    private int bombSpeed;

    private int score;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    // Status
    private Boolean action_flg = false;
    private Boolean start_flg = false;
    private Boolean pause_flg = false;

    int action;
    int coins = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences settings = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        action = settings.getInt("ACTION", 1);
        coins = settings.getInt("COINS", 250); // (250 for Test - Default is 0)

        // FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sound = new SoundEffects(this);

        // Mapeamento dos Elementos
        scoreLabel = findViewById(R.id.score_lb);
        startLabel = findViewById(R.id.startLb);
        pauseLb = findViewById(R.id.pause_lb);
        player = findViewById(R.id.player);
        ruby = findViewById(R.id.ruby);
        diamond = findViewById(R.id.diamond);
        skull = findViewById(R.id.skull);
        bomb = findViewById(R.id.bomb);

        startLb = findViewById(R.id.start_lb);
        frameLb = findViewById(R.id.frame_lb);
        tv_coins = findViewById(R.id.tv_coins);

        WindowManager windowManager = getWindowManager();
        Display disp = windowManager.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        pauseLb.setEnabled(false);
        frameLb.setVisibility(View.GONE);

        screenWidth = size.x;
        //int screenHeight = size.y;

        playerSpeed = Math.round(screenWidth / 59);
        rubySpeed = Math.round(screenWidth / 57);
        diamondSpeed = Math.round(screenWidth / 36);
        skullSpeed = Math.round(screenWidth / 42);
        bombSpeed = Math.round(screenWidth / 39);

        ruby.setX(-80f);
        ruby.setY(-80f);
        diamond.setX(-80f);
        diamond.setY(-80f);
        skull.setX(-80f);
        skull.setY(-80f);
        bomb.setX(-80f);
        bomb.setY(-80f);

        scoreLabel.setText("Score: 0");
        tv_coins.setText("" + coins);
    }

    @SuppressLint("SetTextI18n")
    public void position() {

        // Função de Colisão entre os objectos e o jogador (Pontos e Game Over)
        hit();

        // Velocidades e Posição dos Objectos e do Jogador
        // Diamond
        diamondX -= diamondSpeed;
        if (diamondX < 0) {
            diamondX = screenWidth + 3000;
            diamondY = (int) Math.floor(Math.random() * (frameHeight - diamond.getHeight()));
        }
        diamond.setX(diamondX);
        diamond.setY(diamondY);

        // Ruby
        rubyX -= rubySpeed;
        if (rubyX < 0) {
            rubyX = screenWidth + 3000;
            rubyY = (int) Math.floor(Math.random() * (frameHeight - ruby.getHeight()));
        }
        ruby.setX(rubyX);
        ruby.setY(rubyY);

        // Skull
        skullX -= skullSpeed;
        if (skullX < 0) {
            skullX = screenWidth + 3000;
            skullY = (int) Math.floor(Math.random() * (frameHeight - skull.getHeight()));
        }
        skull.setX(skullX);
        skull.setY(skullY);

        // Bomb
        bombX -= bombSpeed;
        if (bombX < 0) {
            bombX = screenWidth + 3000;
            bombY = (int) Math.floor(Math.random() * (frameHeight - bomb.getHeight()));
        }
        bomb.setX(bombX);
        bomb.setY(bombY);

        // Player
        if (action_flg) {
            playerY -= playerSpeed;
            if (action == 1) {
                player.setImageResource(R.drawable.plane);
            } else if (action == 2) {
                player.setImageResource(R.drawable.plane2);
            }
            if (action == 3) {
                player.setImageResource(R.drawable.plane3);
            } else if (action == 4) {
                player.setImageResource(R.drawable.plane4);
            }
            // player.setImageResource(getResources().getIdentifier("player"+action+"", "drawable", getPackageName()));
        } else {
            playerY += playerSpeed;
            // player.setImageResource(getResources().getIdentifier("player"+action+"", "drawable", getPackageName()));
        }

        if (playerY < 0) playerY = 0;

        if (playerY > frameHeight - playerSize) {
            playerY = frameHeight - playerSize;
        }

        player.setY(playerY);

        scoreLabel.setText("Score: " + score);
        tv_coins.setText("" + coins);
    }

    // Função do Movimento do Jogador
    public boolean onTouchEvent(MotionEvent ME) {

        if (!start_flg) {

            start_flg = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            playerY = (int) player.getY();

            playerSize = player.getHeight();

            startLabel.setVisibility(View.GONE);

            pauseLb.setEnabled(true);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            position();
                        }
                    });
                }
            }, 0, 20);

        } else {

            if (ME.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else {
                if (ME.getAction() == MotionEvent.ACTION_UP) {
                    action_flg = false;
                }
            }
        }
        return true;
    }

    // Função de Colisão entre os objectos e o jogador (Pontos e Game Over)
    public void hit() {

        // Diamond Hit
        int diamondCenterX = diamondX + diamond.getWidth() / 2;
        int diamondCenterY = diamondY + diamond.getHeight() / 2;

        if (0 <= diamondCenterX && diamondCenterX <= playerSize &&
                playerY <= diamondCenterY && diamondCenterY <= playerY + playerSize) {

            score += 1;
            diamondX = -10;
            sound.collectSound();
        }

        // Ruby Hit
        int rubyCenterX = rubyX + ruby.getWidth() / 2;
        int rubyCenterY = rubyY + ruby.getHeight() / 2;

        if (0 <= rubyCenterX && rubyCenterX <= playerSize &&
                playerY <= rubyCenterY && rubyCenterY <= playerY + playerSize) {

            coins++;
            score += 3;
            rubyX = -10;
            sound.collectSound();
        }

        // Skull Hit
        int skullCenterX = skullX + skull.getWidth() / 2;
        int skullCenterY = skullY + skull.getHeight() / 2;

        if (0 <= skullCenterX && skullCenterX <= playerSize &&
                playerY <= skullCenterY && skullCenterY <= playerY + playerSize) {

            timer.cancel();
            timer = null;
            sound.loseSound();

            SharedPreferences settings = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("COINS", coins);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }

        // Bomb Hit
        int bombCenterX = bombX + bomb.getWidth() / 2;
        int bombCenterY = bombY + bomb.getHeight() / 2;

        if (0 <= bombCenterX && bombCenterX <= playerSize &&
                playerY <= bombCenterY && bombCenterY <= playerY + playerSize) {

            timer.cancel();
            timer = null;
            sound.loseSound();

            SharedPreferences settings = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("COINS", coins);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    // Evento do Pause do Jogo
    public void pauseGame(View view) {

        if (!pause_flg) {
            pause_flg = true;

            timer.cancel();
            timer = null;

            Drawable d = getResources().getDrawable(R.drawable.ic_action_paused);
            pauseLb.setBackground(d);

            frameLb.setVisibility(View.VISIBLE);
        } else {
            pause_flg = false;

            Drawable d = getResources().getDrawable(R.drawable.ic_action_pause);
            pauseLb.setBackground(d);

            frameLb.setVisibility(View.GONE);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            position();
                        }
                    });
                }
            }, 0, 20);
        }
    }
}