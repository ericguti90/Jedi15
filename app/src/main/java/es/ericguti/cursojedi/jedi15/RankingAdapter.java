package es.ericguti.cursojedi.jedi15;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.AdapterViewHolderRanking> {
    ArrayList<Ranking> ranking;

    RankingAdapter(ArrayList<Ranking> list){
        ranking = new ArrayList<Ranking>();
        ranking = list;
    }

    @Override
    public AdapterViewHolderRanking onCreateViewHolder(ViewGroup parent, int viewType) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.row_ranking_layout, parent, false);
        return new AdapterViewHolderRanking(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolderRanking holder, int position) {
        holder.name.setText(ranking.get(position).name);
        holder.points.setText(ranking.get(position).points+"");
        if(position == 0) holder.logo.setImageResource(R.drawable.ranking1);
        else if (position == 1) holder.logo.setImageResource(R.drawable.ranking2);
        else if (position == 2) holder.logo.setImageResource(R.drawable.ranking3);
        else holder.position.setText(position+1+"º");
    }

    @Override
    public int getItemCount() {
        return ranking.size();
    }

    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolderRanking extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView points;
        public TextView position;
        public ImageView logo;

        public AdapterViewHolderRanking(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.textView13);
            this.points = (TextView) itemView.findViewById(R.id.textView14);
            this.logo = (ImageView) itemView.findViewById(R.id.imageView58);
            this.position = (TextView) itemView.findViewById(R.id.textView15);
        }
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */


    }
}
