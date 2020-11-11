package com.coelho.flightgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    int highScore;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // MAPPING ELEMENTS
        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);
        TextView gamesPlayedLabel = findViewById(R.id.gamesPlayedLabel);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText("Score: " + score);

        // HIGHSCORE
        SharedPreferences preferencesScore = getSharedPreferences("HIGHSCORE", Context.MODE_PRIVATE);
        highScore = preferencesScore.getInt("HIGHSCORE", 0);

        if (score > highScore) {
            highScoreLabel.setText("HighScore: " + score);

            SharedPreferences.Editor editor = preferencesScore.edit();
            editor.putInt("HIGHSCORE", score);
            editor.apply();
        } else {
            highScoreLabel.setText("HighScore: " + highScore);
        }

        // GAMES PLAYED
        SharedPreferences preferencesGames = getSharedPreferences("GAMES", Context.MODE_PRIVATE);
        int games = preferencesGames.getInt("GAMES", 0);

        gamesPlayedLabel.setText("Games Played: " + (games + 1));

        SharedPreferences.Editor editor = preferencesGames.edit();
        editor.putInt("GAMES", (games + 1));
        editor.apply();
    }

    // TRY AGAIN OR RESTART
    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), StartActivity.class));
        finish();
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
}