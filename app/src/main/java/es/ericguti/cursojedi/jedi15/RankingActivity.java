package es.ericguti.cursojedi.jedi15;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RankingActivity extends ActionBarActivity implements ConfirmacionRanking.borrarRankingI {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    private boolean mRanking = true;
    ConfirmacionRanking dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView2);
        TextView yoda1 = (TextView) findViewById(R.id.textView16);
        TextView yoda2 = (TextView) findViewById(R.id.textView11);
        ImageView yoda = (ImageView) findViewById(R.id.imageView54);
        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(this);
        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);

        MyBD bdUsers = new MyBD(this);
        SQLiteDatabase db = bdUsers.getWritableDatabase();
        ArrayList<Ranking> ranking = new ArrayList<Ranking>();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT user,points FROM ranking ORDER BY points ASC", null);
            if (c.moveToFirst()) {
                do {
                    ranking.add(new Ranking(c.getString(0),c.getInt((1))));
                } while (c.moveToNext());
            }
            else {
                mRanking = false;
                yoda1.setText(R.string.fraseYoda1);
                yoda2.setText(R.string.fraseYoda2);
                Picasso.with(getApplicationContext()).load(R.drawable.yoda).resize(200, 200).transform(new CircleTransform()).into(yoda);
            }
        }
        db.close();
        mRecyclerView.setAdapter(new RankingAdapter(ranking));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
        MenuItem myDynamicMenuItem = menu.findItem(R.id.action_settings);
        if(!mRanking)myDynamicMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            dialogo = new ConfirmacionRanking();
            dialogo.show(fragmentManager, "tagAlerta");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void borrarRanking() {
        MyBD bdUsers = new MyBD(getApplicationContext());
        SQLiteDatabase db = bdUsers.getWritableDatabase();
        if (db != null) db.execSQL("DELETE FROM ranking");
        db.close();
        dialogo.dismiss();
        recreate();
    }
}


