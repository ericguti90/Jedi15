package es.ericguti.cursojedi.jedi15;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by inlab on 09/07/2015.
 */
public class MusicBoundService extends Service {

    private final IBinder mBinder = new MyBinder();
    static String actual="";
    static int posActual = 0;
    static ArrayList<Song> songs;
    static MediaPlayer mediaPlayer;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return mBinder;
    }

    public class MyBinder extends Binder {
        MusicBoundService getService() {
            return MusicBoundService.this;
        }
    }



    public static void startMusic(int pos){
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

    public static void startPauseMusic(){
        if(actual.equals("")) {
            MusicActivity.switchPlayPause(true);
            startMusic(posActual);
        }
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            MusicActivity.switchPlayPause(false);
        }
        else {
            mediaPlayer.start();
            MusicActivity.switchPlayPause(true);
        }
    }

    public void click(final int position){
        if(actual.equals("")) {
            MusicActivity.switchPlayPause(true);
            startMusic(position);
        }
        else if (actual.equals(songs.get(position).name)){
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                MusicActivity.switchPlayPause(false);
            }
            else {
                mediaPlayer.start();
                MusicActivity.switchPlayPause(true);
            }
        }
        else {
            MusicActivity.switchPlayPause(true);
            startMusic(position);
        }
    }

    public void loadSong(ArrayList<Song> s){
        songs = s;
    }
}

