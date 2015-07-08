package es.ericguti.cursojedi.jedi15;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class RankingActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView2);
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
            //db.execSQL("INSERT INTO usuaris VALUES ('eric1','eric','plaza','img')");
            Cursor c = db.rawQuery("SELECT user,points FROM ranking ORDER BY points ASC", null);
            if (c.moveToFirst()) {
                do {
                    ranking.add(new Ranking(c.getString(0),c.getInt((1))));
                } while (c.moveToNext());
            }
        }
        db.close();
        mRecyclerView.setAdapter(new RankingAdapter(ranking));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
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
