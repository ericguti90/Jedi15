package es.ericguti.cursojedi.jedi15;

import android.app.Activity;
import android.app.DialogFragment;
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
    MusicActivity mActivity;
    private Callback listener;

    MyMusicAdapter(ArrayList<Song> songs, MusicActivity m){
        this.songs = songs;
        listener = (Callback) m;
        this.mActivity = m;
    }

    @Override
    public MyMusicAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_music_layout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, final int position) {
        holder.name.setText(songs.get(position).name);
        View.OnClickListener titlelistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(position);
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

    public static interface Callback {
        public void click(int position);
    }
}
