package es.ericguti.cursojedi.jedi15;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MusicActivity extends ActionBarActivity implements View.OnClickListener, MyMusicAdapter.Callback{
    public MusicBoundService mService;
    boolean mBound = false;
    int[] viewCoords = new int[2];
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    static ImageView play;
    private ArrayList<Song> songs;
    int imageX, imageY;
    double finalHeight, finalWidth;
    static boolean playMusic = false;
    boolean loadMusic = false;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        play = (ImageView) findViewById(R.id.imageView56);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.

        mLinearLayout = new LinearLayoutManager(this);
        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);
        songs = new ArrayList<Song>();

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v("MUSIC","osc");
                MusicBoundService.MyBinder binder = (MusicBoundService.MyBinder) service;
                mService = binder.getService();
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.v("MUSIC","osd");
                mBound = false;
            }
        };

        // Se vincula al LocalService
        Intent intent = new Intent(MusicActivity.this, MusicBoundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        cargarMusica();
        mRecyclerView.setAdapter(new MyMusicAdapter(songs, this));

        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
                imageY = touchY - viewCoords[1]; // viewCoords[1] is the y coordinate
                finalHeight = play.getMeasuredHeight();
                finalWidth = play.getMeasuredWidth();
                return false;
            }
        });
        play.setOnClickListener(this);
    }

    private void cargarMusica() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int dataColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String thisTitle = musicCursor.getString(titleColumn);
                String thisData = musicCursor.getString(dataColumn);
                songs.add(new Song (thisData,thisTitle));
            }
            while (musicCursor.moveToNext());
        }
    }


    public static void switchPlayPause(boolean music){
        playMusic = music;
        if (music) play.setImageResource(R.drawable.menu_pause);
        else play.setImageResource(R.drawable.menu_play);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(mBound && !loadMusic) {
            mService.loadSong(songs);
            loadMusic=true;
        }
        if(loadMusic) {
            if (imageX > finalWidth / 3 && imageX < (finalWidth / 3) * 2 && imageY < finalHeight / 2) {
                mService.startPauseMusic();
            }
            if (imageX > finalWidth / 3 && imageX < (finalWidth / 3) * 2 && imageY > finalHeight / 2) {
                Intent intent = new Intent(getApplicationContext(), YodaActivity.class);
                startActivity(intent);
            } else if (imageX < finalWidth / 2 && imageY > finalHeight / 3 && imageY < (finalHeight / 3) * 2)
                mService.startMusic(mService.posActual - 1);
            else if (imageX > finalWidth / 2 && imageY > finalHeight / 3 && imageY < (finalHeight / 3) * 2)
                mService.startMusic(mService.posActual + 1);
        }
    }

    @Override
    public void click(int position) {
        if(mBound) {
            if(loadMusic) mService.click(position);
            else {
                mService.loadSong(songs);
                loadMusic = true;
                mService.click(position);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("play", playMusic);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playMusic = savedInstanceState.getBoolean("play");
        if(playMusic) play.setImageResource(R.drawable.menu_pause);
        else play.setImageResource(R.drawable.menu_play);
    }
}
