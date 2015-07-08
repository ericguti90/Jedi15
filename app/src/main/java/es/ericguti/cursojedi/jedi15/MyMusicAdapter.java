package es.ericguti.cursojedi.jedi15;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by inlab on 07/07/2015.
 */
public class MyMusicAdapter extends RecyclerView.Adapter<MyMusicAdapter.AdapterViewHolder> {
    static ArrayList<Song> songs;
    static MediaPlayer mediaPlayer;
    static String actual="";
    static int posActual = 0;

    MyMusicAdapter(ArrayList<Song> songs){
        this.songs = songs;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public MyMusicAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_music_layout, parent, false);
        return new AdapterViewHolder(view);
    }

    public static void startMusic(int pos){
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

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, final int position) {
        holder.name.setText(songs.get(position).name);
        View.OnClickListener titlelistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setBackgroundColor(R.color.abc_background_cache_hint_selector_material_dark);
                //v.setBackgroundResource(R.drawable.carta);
                //Toast.makeText(v.getContext(),songs.get(position).name , Toast.LENGTH_SHORT).show();

                if(actual.equals("")) {
                    MusicActivity.switchPlayPause(true);
                    startMusic(position);
                }
                else if (actual.equals(songs.get(position).name)){
                    //Toast.makeText(v.getContext(),songs.get(position).name , Toast.LENGTH_SHORT).show();
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
                notifyDataSetChanged();
            }
        };
        holder.itemView.setOnClickListener(titlelistener);
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.textView12);
        }
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */


    }
}
