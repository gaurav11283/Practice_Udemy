package com.example.practiceudemy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class StandAloneActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnVideo;
    private  Button btnPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stand_alone);
        btnVideo = findViewById(R.id.btnvideo);
        btnPlaylist = findViewById(R.id.btnPlaylist);
        btnPlaylist.setOnClickListener(this);
        btnVideo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();

        switch(id){
            case R.id.btnvideo:
                intent = YouTubeStandalonePlayer.createVideoIntent(this,YouTubeActivity.GOOGLE_API_KEY,YouTubeActivity.YOUTUBE_VIDEO_ID);
                break;
            case R.id.btnPlaylist:
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this,YouTubeActivity.GOOGLE_API_KEY,YouTubeActivity.YOUTUBE_PLAYLIST_ID);
                break;
            default:
        }
        if(intent!=null){
            startActivity(intent);
        }
    }
}