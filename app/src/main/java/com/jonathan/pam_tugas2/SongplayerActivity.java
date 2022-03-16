package com.jonathan.pam_tugas2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class SongplayerActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView songName;
    private ImageView btnPlayPause, btnNext, btnPrevious, btnLoop, btnShuffle, btnQueue;
    private MediaPlayer mediaPlayer;

    int position;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songplayer);

        seekBar = findViewById(R.id.seekBar);
        songName = findViewById(R.id.songName);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnLoop = findViewById(R.id.btnLoop);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnQueue = findViewById(R.id.btnQueue);






        //Back button
        ImageView imageBack = findViewById(R.id.btnBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Navbar bawah
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setSelectedItemId(R.id.player_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.player_menu:
                        return true;
                    case R.id.home_menu:
                        onBackPressed();
                        overridePendingTransition(0, 0);
                        return false;
                }
                return false;
            }
        });

    }
}