package es.ericguti.cursojedi.jedi15;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Struct;
import java.util.ArrayList;


public class MusicActivity extends ActionBarActivity{
    TextView text;
    int[] viewCoords = new int[2];
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;

    private ArrayList<Song> songs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        final ImageView play = (ImageView) findViewById(R.id.imageView56);
        text = (TextView) findViewById(R.id.textView11);

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
        mRecyclerView.setAdapter(new MyMusicAdapter(songs));







        //ListView music = (ListView) findViewById(R.id.listView);
        play.getLocationOnScreen(viewCoords);
        ViewTreeObserver vto = play.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                play.getViewTreeObserver().removeOnPreDrawListener(this);
                double finalHeight = play.getMeasuredHeight();
                double finalWidth = play.getMeasuredWidth();
                text.setText("Height: " + finalHeight + " Width: " + finalWidth);
                return true;
            }
        });

                //text.setText(play.getMeasuredWidth() + " " + play.getMeasuredHeight());
        play.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch (View v, MotionEvent event) {
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                int imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
                int imageY = touchY - viewCoords[1]; // viewCoords[1] is the y coordinate
                text.setText(imageX + " - " + imageY);
                return false;
            }
        });

    }

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
}
