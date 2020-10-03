package com.coelho.flightgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    ImageButton play, more, shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Make Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        play = findViewById(R.id.play);
        more = findViewById(R.id.more);
        shop = findViewById(R.id.shop);

        // Evento do Botão Play
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        // Evento da Loja
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, ShopActivity.class));
                finish();
            }
        });

        // Evento do Botão More
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(StartActivity.this, more);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("IntentReset")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        Intent intent, chooser;
                        int id = menuItem.getItemId();
                        if (id == R.id.feedback) {
                            intent = new Intent(Intent.ACTION_SEND);
                            intent.setData(Uri.parse("mailto:"));
                            String[] to = {"coelho301181@gmail.com"}; // My Email
                            intent.putExtra(Intent.EXTRA_EMAIL, to);
                            intent.setType("message/rfc822");
                            chooser = Intent.createChooser(intent, "Send Feedback");
                            startActivity(chooser);
                        }

                        if (id == R.id.share) {
                            intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Flight Game");
                            String sAux = "\n Let me Recomend you this Game \n\n";
                            sAux = sAux + "Link do Google Play";

                            intent.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(intent, "Share"));
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    // Disable Return Button
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