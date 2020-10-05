package com.coelho.flightgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {

    RelativeLayout shopP1, shopP2, shopP3, shopP4;
    RelativeLayout unlock2, unlock3, unlock4;

    ImageButton goHome;
    TextView tv_coins;

    boolean shop2, shop3, shop4;

    int coins, action;

    // FULLSCREEN
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        } else {
            showSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopP1 = findViewById(R.id.shopP1);
        shopP2 = findViewById(R.id.shopP2);
        shopP3 = findViewById(R.id.shopP3);
        shopP4 = findViewById(R.id.shopP4);

        unlock2 = findViewById(R.id.unlock2);
        unlock3 = findViewById(R.id.unlock3);
        unlock4 = findViewById(R.id.unlock4);

        goHome = findViewById(R.id.home);
        tv_coins = findViewById(R.id.tv_coins);

        final SharedPreferences settings = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        coins = settings.getInt("COINS", 0);
        action = settings.getInt("ACTION", 1);
        shop2 = settings.getBoolean("SHOP2", false);
        shop3 = settings.getBoolean("SHOP3", false);
        shop4 = settings.getBoolean("SHOP4", false);

        tv_coins.setText("" + coins);

        if (shop2) {
            unlock2.setVisibility(View.GONE);
        }

        if (shop3) {
            unlock3.setVisibility(View.GONE);
        }

        if (shop4) {
            unlock4.setVisibility(View.GONE);
        }

        shopP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action = 1;

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("ACTION", action);
                editor.apply();
                startActivity(new Intent(ShopActivity.this, StartActivity.class));
            }
        });

        shopP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shop2) {
                    action = 2;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("ACTION", action);
                    editor.apply();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));

                } else if (coins >= 30) {

                    shop2 = true;
                    action = 2;
                    coins = coins - 30;

                    tv_coins.setText("" + coins);
                    unlock2.setVisibility(View.GONE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("ACTION", action);
                    editor.putInt("COINS", coins);
                    editor.putBoolean("SHOP2", shop2);
                    editor.apply();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));
                } else {
                    Toast.makeText(ShopActivity.this, "Not enough coins", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shopP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shop3) {
                    action = 3;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("ACTION", action);
                    editor.apply();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));

                } else if (coins >= 60) {

                    shop3 = true;
                    action = 3;
                    coins = coins - 60;

                    tv_coins.setText("" + coins);
                    unlock3.setVisibility(View.GONE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("ACTION", action);
                    editor.putInt("COINS", coins);
                    editor.putBoolean("SHOP3", shop3);
                    editor.apply();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));
                } else {
                    Toast.makeText(ShopActivity.this, "Not enough coins", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shopP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shop4) {
                    action = 4;

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("ACTION", action);
                    editor.apply();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));

                } else if (coins >= 90) {

                    shop4 = true;
                    action = 4;
                    coins = coins - 90;

                    tv_coins.setText("" + coins);
                    unlock4.setVisibility(View.GONE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("ACTION", action);
                    editor.putInt("COINS", coins);
                    editor.putBoolean("SHOP4", shop4);
                    editor.apply();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));
                } else {
                    Toast.makeText(ShopActivity.this, "Not enough coins", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, StartActivity.class));
                finish();
            }
        });
    }
}