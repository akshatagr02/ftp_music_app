package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.io.File;

public class main_music extends AppCompatActivity {
    private TextView title;
    private ExoPlayer exo;
    private  PlayerView player;
    String uri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_music);
        String ct =getIntent().getStringExtra("title");
        title = findViewById(R.id.title_music);
        title.setText(ct);
        uri = getIntent().getStringExtra("fileuri");
        //Toast.makeText(this, uri, Toast.LENGTH_SHORT).show();
        player = findViewById(R.id.playerview);
        exo = new ExoPlayer.Builder(this).build();

        // Bind the player to the view.
        player.setPlayer(exo);
        Uri uris = Uri.parse(uri);
        MediaItem mediaItem = MediaItem.fromUri(uris);

        exo.setMediaItem(mediaItem);

        exo.prepare();

        exo.setPlayWhenReady(true);
        exo.play();
title.setSelected(true);

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (exo != null) {
            exo.release();
            exo = null;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            File file = new File(uri);
            file.delete();
            Intent intent = new Intent(main_music.this,MainActivity.class);
            startActivity(intent);

        }
        return super.onKeyDown(keyCode, event);
    }
}