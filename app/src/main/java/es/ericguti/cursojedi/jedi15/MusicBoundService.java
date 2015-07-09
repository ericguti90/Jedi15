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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("service", "onbind");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return mBinder;
    }

    public class MyBinder extends Binder {
        MusicBoundService getService() {
            Log.v("service", "getservice");
            return MusicBoundService.this;
        }
    }

    static MediaPlayer mediaPlayer;

    /*public void iniMediaPlayer(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }*/

    public static void startMusic(int pos){
        Log.v("service", "startmusic");
        try {
            if(pos<1) pos = songs.size()-1;
            else if(pos>songs.size()-1) pos = 0;
            posActual=pos;
            actual = songs.get(pos).name;
            //mediaPlayer.stop();
            mediaPlayer.reset();
            //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(songs.get(pos).url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startPauseMusic(){
        Log.v("service", "startpause");
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
        Log.v("service", "click");
        if(actual.equals("")) {
            Log.v("service", "switchplaypause");
            MusicActivity.switchPlayPause(true);
            startMusic(position);
        }
        else if (actual.equals(songs.get(position).name)){
            //Toast.makeText(v.getContext(),songs.get(position).name , Toast.LENGTH_SHORT).show();
            if(mediaPlayer.isPlaying()) {
                Log.v("service", "switchplaypause");
                mediaPlayer.pause();
                MusicActivity.switchPlayPause(false);
            }
            else {
                mediaPlayer.start();
                MusicActivity.switchPlayPause(true);
            }
        }
        else {
            Log.v("service", "switchplaypause");
            MusicActivity.switchPlayPause(true);
            startMusic(position);
        }
    }
}

