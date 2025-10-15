package com.example.tripbuddy20;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Gallery extends AppCompatActivity {

    ImageView fullImage;
    Button btnMute, back;
    MediaPlayer mediaPlayer;
    Uri audioUri;
    boolean isMuted = false;
    float currentVolume = 1.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        fullImage = findViewById(R.id.fullscreenImageView);
        btnMute = findViewById(R.id.btnMute);
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        String imageUriStr = intent.getStringExtra("imageUri");
        String audioUriStr = intent.getStringExtra("audioUri");

        if (imageUriStr != null) {
            fullImage.setImageURI(Uri.parse(imageUriStr));
        }

        if (audioUriStr != null) {
            audioUri = Uri.parse(audioUriStr);
            playAudio(); // Auto-play on open
        }

        btnMute.setOnClickListener(v -> toggleMute());
    }

    private void playAudio() {
        if (audioUri == null) return;

        mediaPlayer = MediaPlayer.create(this, audioUri);
        mediaPlayer.setVolume(currentVolume, currentVolume); // Initial volume
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    }

    private void toggleMute() {
        if (mediaPlayer == null) return;

        if (isMuted) {
            currentVolume = 1.0f;
            btnMute.setText("Mute");
        } else {
            currentVolume = 0.0f;
            btnMute.setText("Unmute");
        }

        isMuted = !isMuted;
        mediaPlayer.setVolume(currentVolume, currentVolume);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

