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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        play = (ImageView) findViewById(R.id.imageView56);
        //text = (TextView) findViewById(R.id.textView11);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(this);
        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);
        songs = new ArrayList<Song>();
        cargarMusica();
        //El adapter se encarga de  adaptar un objeto definido en el código a una vista en xml
        //según la estructura definida.
        //Asignamos nuestro custom Adapter
        mRecyclerView.setAdapter(new MyMusicAdapter(songs, this));







        connection = new ServiceConnection() {
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










        //ListView music = (ListView) findViewById(R.id.listView);
        //play.getLocationOnScreen(viewCoords);
        /*ViewTreeObserver vto = play.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                play.getViewTreeObserver().removeOnPreDrawListener(this);
                double finalHeight = play.getMeasuredHeight();
                double finalWidth = play.getMeasuredWidth();
                text.setText("Height: " + finalHeight + " Width: " + finalWidth);
                return true;
            }
        });*/

                //text.setText(play.getMeasuredWidth() + " " + play.getMeasuredHeight());
        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
                imageY = touchY - viewCoords[1]; // viewCoords[1] is the y coordinate
                //text.setText(imageX + " - " + imageY);
                finalHeight = play.getMeasuredHeight();
                finalWidth = play.getMeasuredWidth();
                return false;
            }
        });
        play.setOnClickListener(this);



    }

    private ServiceConnection connection;

    private void cargarMusica() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int dataColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            //int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            //int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                //long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisData = musicCursor.getString(dataColumn);
                //String thisArtist = musicCursor.getString(artistColumn);
                //songList.add(new Song(thisId, thisTitle, thisArtist));
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(imageX>finalWidth/3 && imageX<(finalWidth/3)*2 && imageY < finalHeight/2) {
            mService.startPauseMusic();
        }
        if(imageX>finalWidth/3 && imageX<(finalWidth/3)*2 && imageY > finalHeight/2) {

            Intent intent = new Intent(getApplicationContext(), YodaActivity.class);
            startActivity(intent);
        }
        else if(imageX<finalWidth/2 && imageY>finalHeight/3 && imageY<(finalHeight/3)*2) mService.startMusic(mService.posActual-1);//Toast.makeText(v.getContext(), "retroceder", Toast.LENGTH_SHORT).show();
        else if(imageX>finalWidth/2 && imageY>finalHeight/3 && imageY<(finalHeight/3)*2) mService.startMusic(mService.posActual+1);//Toast.makeText(v.getContext(), "avanzar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(int position) {
        Log.v("MUSIC","click");
        if(mBound) {
            Log.v("MUSIC","is bound");
            mService.click(position);
        }
        //unbindService(connection);
    }
}
