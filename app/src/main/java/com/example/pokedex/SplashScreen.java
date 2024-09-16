package com.example.pokedex;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3800; // 3 seconds duration
    private MediaPlayer mediaPlayer; // MediaPlayer for audio playback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView gifImageView = findViewById(R.id.logo);

        // Set the status bar color for the splash screen
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.splashtop));

        // Load the GIF using Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.pokesplash)
                .into(gifImageView);

        // Initialize MediaPlayer to play the audio (put your audio in res/raw folder)
        mediaPlayer = MediaPlayer.create(this, R.raw.pokedex_sound); // Make sure to place splash_audio.mp3 in res/raw
        mediaPlayer.start(); // Start playing the audio

        // Start the MainActivity after SPLASH_TIME_OUT
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Disable transition animations
            finish();

            // Stop and release the MediaPlayer after the splash screen
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer if it is still active
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
