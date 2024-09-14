package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000; // Set the timeout to 3 seconds or adjust as per GIF duration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView gifImageView = findViewById(R.id.logo);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.splashtop));

        // Load the uploaded GIF
        Glide.with(this)
                .asGif()
                .load(R.drawable.pokesplash)
                .into(gifImageView);

        // Start the MainActivity after SPLASH_TIME_OUT
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Disable transition animations
            finish();
        }, SPLASH_TIME_OUT);
    }
}
