package com.example.tripbuddy20;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Memories extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_AUDIO = 2;

    ImageView image;
    TextView Music;
    Button btnUpload, btnPlay, btnPause, btnStop, exit ;
    Uri selectedImageUri;
    Uri selectedAudioUri;

    MediaPlayer media;

    MemoryDB memoryDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        exit = findViewById(R.id.Exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPause = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);

        btnPlay.setOnClickListener(v -> playAudio());
        btnPause.setOnClickListener(v -> pauseAudio());
        btnStop.setOnClickListener(v -> stopAudio());

        memoryDB = new MemoryDB(this);

        Log.d(TAG, "onCreate: Initializing views");
        image = findViewById(R.id.memimage);
        btnUpload = findViewById(R.id.btnUpload);
        Music = findViewById(R.id.music);

        if (image != null) image.setOnClickListener(v -> pickImage());
        if (Music != null) Music.setOnClickListener(v -> pickAudio());
        if (btnUpload != null) btnUpload.setOnClickListener(v -> uploadMemory());

        Log.d(TAG, "onCreate: View initialization and listeners set.");
    }

    private void pickImage() {
        Log.d(TAG, "pickImage: Launching image picker");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void pickAudio() {
        Log.d(TAG, "pickAudio: Launching audio picker");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(Intent.createChooser(intent, "Select Audio"), PICK_AUDIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            Log.d(TAG, "onActivityResult: File URI: " + fileUri.toString());
            if (requestCode == PICK_IMAGE) {
                selectedImageUri = fileUri;
                if (image != null) image.setImageURI(fileUri);
            } else if (requestCode == PICK_AUDIO) {
                selectedAudioUri = fileUri;
                if (Music != null) Music.setText(getFileName(fileUri));
            }
        }
    }

    private void uploadMemory() {
        Log.d(TAG, "uploadMemory: Attempting to upload memory.");
        if (selectedImageUri == null || selectedAudioUri == null) {
            Toast.makeText(this, "Please select both image and audio before uploading.", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUriStr = selectedImageUri.toString();
        String audioUriStr = selectedAudioUri.toString();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


        boolean success = memoryDB.insertMemory(imageUriStr, audioUriStr, timestamp);

        if (success) {
            Toast.makeText(this, "Memory saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save memory.", Toast.LENGTH_SHORT).show();
        }

        // Clear selections
        selectedImageUri = null;
        selectedAudioUri = null;
        if (image != null) image.setImageResource(android.R.color.transparent);
        if (Music != null) Music.setText("Tap to select music");
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri == null) return "Unknown File";

        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) result = cursor.getString(nameIndex);
                }
            } catch (Exception e) {
                Log.e(TAG, "getFileName: Error querying content resolver");
            }
        }

        if (result == null) {
            result = uri.getPath();
            if (result != null) {
                int lastSlash = result.lastIndexOf('/');
                if (lastSlash != -1) result = result.substring(lastSlash + 1);
            } else {
                result = "Unknown File";
            }
        }
        return result;
    }

    private void playAudio() {
        if (selectedAudioUri == null) {
            Toast.makeText(this, "No audio selected!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (media == null) {
            media = MediaPlayer.create(this, selectedAudioUri);
            media.setOnCompletionListener(mp -> stopAudio()); // Auto stop when finished
        }

        if (!media.isPlaying()) {
            media.start();
            Toast.makeText(this, "Playing audio...", Toast.LENGTH_SHORT).show();
        }
    }

    private void pauseAudio() {
        if (media != null && media.isPlaying()) {
            media.pause();
            Toast.makeText(this, "Audio paused", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio() {
        if (media != null) {
            if (media.isPlaying()) {
                media.stop();
            }
            media.release();
            media = null;
            Toast.makeText(this, "Audio stopped", Toast.LENGTH_SHORT).show();
        }
    }

}