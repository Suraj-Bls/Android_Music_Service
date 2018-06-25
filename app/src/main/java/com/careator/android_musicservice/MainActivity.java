package com.careator.android_musicservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MusicStoppedListener{

    ImageView stopButton;
    boolean musicPlaying = false;
    Intent serviceIntent;
    String audioLink = "https://dl.dropboxusercontent.com/s/5ey5xwb7a5ylqps/game_of_thrones.mp3?dl=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopButton = findViewById(R.id.stopButton);
        stopButton.setBackgroundResource(R.mipmap.play_round);
        serviceIntent = new Intent(this, MyPlayService.class);

        ApplicationClass.context=MainActivity.this;

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!musicPlaying) {
                    playAudio();

                    stopButton.setImageResource(R.mipmap.stop_round);
                    musicPlaying = true;
                } else {
                    stopPlayService();
                    stopButton.setImageResource(R.mipmap.play_round);
                    musicPlaying = false;
                }
            }
        });
    }

    private void playAudio() {
        serviceIntent.putExtra("AudioLink", audioLink);
        try {
            startService(serviceIntent);
        } catch (SecurityException e) {
            Toast.makeText(this, "ERROR" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlayService() {

        try {
            stopService(serviceIntent);
        } catch (SecurityException e) {
            Toast.makeText(this, "ERROR" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMusicStopped() {
        stopButton.setImageResource(R.mipmap.play_round);
        musicPlaying=false;
    }
}

