package com.jonathan.pam_tugas2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;

public class SongplayerActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView songName, nextSongName, durationNow, durationEnd;
    private View outlinePlayPause;
    private ImageView btnPlayPause, btnNext, btnPrevious, btnLoop, btnShuffle, btnQueue;
    private MediaPlayer mediaPlayer;

    int position;
    int nextposition;
    String sname;
    String nextsongname;
    public static final String EXTRA_NAME = "song_name";
    ArrayList<File> mySongs;
    Thread updateseekbar;


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
        outlinePlayPause = findViewById(R.id.outlinePlayPause);
        nextSongName = findViewById(R.id.nextSongName);
        durationNow = findViewById(R.id.durationNow);
        durationEnd = findViewById(R.id.durationEnd);


        if(mediaPlayer != null){
            mediaPlayer.start();
            mediaPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songname = intent.getStringExtra("songname");
        position = bundle.getInt("position", 0);
        nextposition = bundle.getInt("nextposition", 0);
        songName.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname = mySongs.get(position).getName().replace(".mp3", "").replace(".wav", "");
        songName.setText(sname);


        if(nextposition == mySongs.size()){
            nextposition = position-position;
        }
        nextsongname = mySongs.get(nextposition).getName().replace(".mp3", "").replace(".wav", "");
        nextSongName.setText(nextsongname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        //Seekbar
        updateseekbar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentpos = 0;

                while (currentpos<totalDuration){
                    try {
                        sleep(500);
                        currentpos = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentpos);
                    }catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        seekBar.setMax(mediaPlayer.getDuration());
        updateseekbar.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endtime = createTime(mediaPlayer.getDuration());
        durationEnd.setText(endtime);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                durationNow.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);


        //BtnPlayAndPause
        outlinePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    btnPlayPause.setImageResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }else{
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        //NEXT SONG
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%mySongs.size());
                nextposition = ((position+1)%mySongs.size());
                Uri uri = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                sname = mySongs.get(position).getName().replace(".mp3", "").replace(".wav", "");
                nextsongname = mySongs.get(nextposition).getName().replace(".mp3", "").replace(".wav", "");
                songName.setText(sname);
                nextSongName.setText(nextsongname);

                String endtime = createTime(mediaPlayer.getDuration());
                durationEnd.setText(endtime);

                final Handler handler = new Handler();
                final int delay = 1000;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String currentTime = createTime(mediaPlayer.getCurrentPosition());
                        durationNow.setText(currentTime);
                        handler.postDelayed(this, delay);
                    }
                }, delay);

                mediaPlayer.start();
                btnPlayPause.setImageResource(R.drawable.ic_pause);
            }
        });


        //PREVIOUS SONG
            btnPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);
                    nextposition = ((position+1)%mySongs.size());
                    Uri uri = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    sname = mySongs.get(position).getName().replace(".mp3", "").replace(".wav", "");
                    nextsongname = mySongs.get(nextposition).getName().replace(".mp3", "").replace(".wav", "");
                    songName.setText(sname);
                    nextSongName.setText(nextsongname);

                    String endtime = createTime(mediaPlayer.getDuration());
                    durationEnd.setText(endtime);

                    final Handler handler = new Handler();
                    final int delay = 1000;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String currentTime = createTime(mediaPlayer.getCurrentPosition());
                            durationNow.setText(currentTime);
                            handler.postDelayed(this, delay);
                        }
                    }, delay);

                    mediaPlayer.start();
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                }
            });

        //Listener when song end
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnNext.performClick();
            }
        });

        //Back button
        ImageView imageBack = findViewById(R.id.btnBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
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
                        mediaPlayer.stop();
                        onBackPressed();
                        overridePendingTransition(0, 0);
                        return false;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        finish();
    }

    public String createTime(int duration){
        String time = "";
        int min = duration/1000/60;
        int second = duration/1000%60;

        time+=min+":";

        if(second<10){
            time+="0";
        }
        time+=second;

        return time;
    }

}