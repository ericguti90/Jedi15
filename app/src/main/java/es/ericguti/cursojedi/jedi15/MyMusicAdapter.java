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
    ArrayList<Song> songs;
    MediaPlayer mediaPlayer;
    String actual="";

    MyMusicAdapter(ArrayList<Song> songs){
        this.songs = songs;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        /*contactos = new ArrayList<>();
        contactos.add(new String("Benito Camela"));
        contactos.add(new String("Alberto Carlos Huevos"));
        contactos.add(new String("Lola Mento"));
        contactos.add(new String("Aitor Tilla"));
        contactos.add(new String("Elvis Teck"));
        contactos.add(new String("Débora Dora"));
        contactos.add(new String("Borja Món de York"));
        contactos.add(new String("Encarna Vales"));
        contactos.add(new String("Enrique Cido"));
        contactos.add(new String("Francisco Jones"));
        contactos.add(new String("Estela Gartija"));
        contactos.add(new String("Andrés Trozado"));
        contactos.add(new String("Carmelo Cotón"));
        contactos.add(new String("Alberto Mate"));
        contactos.add(new String("Chema Pamundi"));
        contactos.add(new String("Armando Adistancia"));
        contactos.add(new String("Helena Nito Del Bosque"));
        contactos.add(new String("Unai Nomás"));
        contactos.add(new String("Ester Colero"));
        contactos.add(new String("Marcos Corrón"));
        contactos.add(new String("Benito Camela"));
        contactos.add(new String("Alberto Carlos Huevos"));
        contactos.add(new String("Lola Mento"));
        contactos.add(new String("Aitor Tilla"));
        contactos.add(new String("Elvis Teck"));
        contactos.add(new String("Débora Dora"));
        contactos.add(new String("Borja Món de York"));
        contactos.add(new String("Encarna Vales"));
        contactos.add(new String("Enrique Cido"));
        contactos.add(new String("Francisco Jones"));
        contactos.add(new String("Estela Gartija"));
        contactos.add(new String("Andrés Trozado"));
        contactos.add(new String("Carmelo Cotón"));
        contactos.add(new String("Alberto Mate"));
        contactos.add(new String("Chema Pamundi"));
        contactos.add(new String("Armando Adistancia"));
        contactos.add(new String("Helena Nito Del Bosque"));
        contactos.add(new String("Unai Nomás"));
        contactos.add(new String("Ester Colero"));
        contactos.add(new String("Marcos Corrón"));
        contactos.add(new String("Benito Camela"));
        contactos.add(new String("Alberto Carlos Huevos"));
        contactos.add(new String("Lola Mento"));
        contactos.add(new String("Aitor Tilla"));
        contactos.add(new String("Elvis Teck"));
        contactos.add(new String("Débora Dora"));
        contactos.add(new String("Borja Món de York"));
        contactos.add(new String("Encarna Vales"));
        contactos.add(new String("Enrique Cido"));
        contactos.add(new String("Francisco Jones"));
        contactos.add(new String("Estela Gartija"));
        contactos.add(new String("Andrés Trozado"));
        contactos.add(new String("Carmelo Cotón"));
        contactos.add(new String("Alberto Mate"));
        contactos.add(new String("Chema Pamundi"));
        contactos.add(new String("Armando Adistancia"));
        contactos.add(new String("Helena Nito Del Bosque"));
        contactos.add(new String("Unai Nomás"));
        contactos.add(new String("Ester Colero"));
        contactos.add(new String("Marcos Corrón"));*/
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
                //v.setBackgroundColor(R.color.abc_background_cache_hint_selector_material_dark);
                //v.setBackgroundResource(R.drawable.carta);
                //Toast.makeText(v.getContext(),songs.get(position).name , Toast.LENGTH_SHORT).show();
                if(actual.equals("")) actual = songs.get(position).name;
                else if (actual.equals(songs.get(position).name)){
                    Toast.makeText(v.getContext(),songs.get(position).name , Toast.LENGTH_SHORT).show();
                    if(mediaPlayer.isPlaying()) mediaPlayer.pause();
                    else mediaPlayer.start();
                }
                try {
                    //mediaPlayer.stop();
                    mediaPlayer.reset();
                    //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(songs.get(position).url);
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
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
