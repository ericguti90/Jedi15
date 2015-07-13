package es.ericguti.cursojedi.jedi15;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextSwitcher;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by inlab on 09/07/2015.
 */
public class MusicBoundService extends Service {

    private final IBinder mBinder = new MyBinder();
    String actual="";
    int posActual = 0;
    ArrayList<Song> songs;
    MediaPlayer mediaPlayer;
    private callbackMusic callback;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                posActual += 1;
                startMusic(posActual);
                callback.switchPlayPause(true);
            }
        });
        return mBinder;
    }

    public class MyBinder extends Binder {
        MusicBoundService getService() {
            return MusicBoundService.this;
        }
    }



    public void startMusic(int pos){
        try {
            if(pos<1) pos = songs.size()-1;
            else if(pos>songs.size()-1) pos = 0;
            posActual=pos;
            actual = songs.get(pos).name;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songs.get(pos).url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPauseMusic(){
        if(actual.equals("")) {
            callback.switchPlayPause(true);
            startMusic(posActual);
        }
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            callback.switchPlayPause(false);
        }
        else {
            mediaPlayer.start();
            callback.switchPlayPause(true);
        }
    }

    public void click(final int position){
        posActual = position;
        if(actual.equals("")) {
            callback.switchPlayPause(true);
            startMusic(position);
        }
        else if (actual.equals(songs.get(position).name)){
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                callback.switchPlayPause(false);
            }
            else {
                mediaPlayer.start();
                callback.switchPlayPause(true);
            }
        }
        else {
            callback.switchPlayPause(true);
            startMusic(position);
        }
    }

    public void loadSong(ArrayList<Song> s, Activity activity){
        songs = s;
        callback = (callbackMusic) activity;
    }

    public static interface callbackMusic {
        void switchPlayPause(boolean b);
    }
}

