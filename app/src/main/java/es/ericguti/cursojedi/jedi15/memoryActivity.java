package es.ericguti.cursojedi.jedi15;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class memoryActivity extends ActionBarActivity implements View.OnClickListener{
    ArrayList<String> cards = new ArrayList<String>(Arrays.asList("jedi", "jedi", "jedi1", "jedi1", "jedi2", "jedi2", "jedi3", "jedi3", "jedi4", "jedi4", "jedi5", "jedi5", "jedi6", "jedi6", "jedi7", "jedi7"));
    ImageView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16;
    TextView text;
    int touch = 0;
    int card1=-1,card2=-1;
    int cId1, cId2;
    int points = 0;
    int finish = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        c1 = (ImageView) findViewById(R.id.imageView32);
        c2 = (ImageView) findViewById(R.id.imageView33);
        c3 = (ImageView) findViewById(R.id.imageView34);
        c4 = (ImageView) findViewById(R.id.imageView35);
        c5 = (ImageView) findViewById(R.id.imageView36);
        c6 = (ImageView) findViewById(R.id.imageView37);
        c7 = (ImageView) findViewById(R.id.imageView38);
        c8 = (ImageView) findViewById(R.id.imageView39);
        c9 = (ImageView) findViewById(R.id.imageView40);
        c10 = (ImageView) findViewById(R.id.imageView41);
        c11 = (ImageView) findViewById(R.id.imageView42);
        c12 = (ImageView) findViewById(R.id.imageView43);
        c13 = (ImageView) findViewById(R.id.imageView44);
        c14 = (ImageView) findViewById(R.id.imageView45);
        c15 = (ImageView) findViewById(R.id.imageView46);
        c16 = (ImageView) findViewById(R.id.imageView47);
        text = (TextView) findViewById(R.id.textView5);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
        c7.setOnClickListener(this);
        c8.setOnClickListener(this);
        c9.setOnClickListener(this);
        c10.setOnClickListener(this);
        c11.setOnClickListener(this);
        c12.setOnClickListener(this);
        c13.setOnClickListener(this);
        c14.setOnClickListener(this);
        c15.setOnClickListener(this);
        c16.setOnClickListener(this);
        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memory, menu);
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
        switch (v.getId()){
            case R.id.imageView32:
                checkCard(v,0);
                break;
            case R.id.imageView33:
                checkCard(v,1);
                break;
            case R.id.imageView34:
                checkCard(v,2);
                break;
            case R.id.imageView35:
                checkCard(v,3);
                break;
            case R.id.imageView36:
                checkCard(v,4);
                break;
            case R.id.imageView37:
                checkCard(v,5);
                break;
            case R.id.imageView38:
                checkCard(v,6);
                break;
            case R.id.imageView39:
                checkCard(v,7);
                break;
            case R.id.imageView40:
                checkCard(v,8);
                break;
            case R.id.imageView41:
                checkCard(v,9);
                break;
            case R.id.imageView42:
                checkCard(v,10);
                break;
            case R.id.imageView43:
                checkCard(v,11);
                break;
            case R.id.imageView44:
                checkCard(v,12);
                break;
            case R.id.imageView45:
                checkCard(v,13);
                break;
            case R.id.imageView46:
                checkCard(v,14);
                break;
            case R.id.imageView47:
                checkCard(v,15);
                break;
        }
    }

    private void checkCard(View v, int card){
        if(card!=card1 && card!=card2) {
            ++touch;
            ((ImageView) findViewById(v.getId())).setImageResource(getResources().getIdentifier("drawable/" + cards.get(card), null, getPackageName()));
            if (touch == 1) {
                card1 = card;
                cId1 = v.getId();
            }
            else {
                card2 = card;
                cId2 = v.getId();
                checkPairCards(cId1,cId2,card1,card2);
            }
        }
    }

    private void checkPairCards(final int cid1, final int cid2, int c1, int c2) {
        ++points;
        text.setText(points+"");
        if(cards.get(c1) == cards.get(c2)){
            Thread th = new Thread(new Runnable() {
                public void run() {
                    ((ImageView) findViewById(cid1)).setOnClickListener(null);
                    ((ImageView) findViewById(cid2)).setOnClickListener(null);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((ImageView) findViewById(cid1)).getHandler().post(new Runnable() {
                        public void run() {
                            ((ImageView) findViewById(cid1)).setVisibility(View.INVISIBLE);
                        }
                    });
                    ((ImageView) findViewById(cid2)).getHandler().post(new Runnable() {
                        public void run() {
                            ((ImageView) findViewById(cid2)).setVisibility(View.INVISIBLE);
                        }
                    });

                }
            });
            th.start();
            ++finish;
            if(finish == 8) exitGame();
        }
        else {
            ((ImageView) findViewById(cId1)).setImageResource(R.drawable.carta);
            ((ImageView) findViewById(cId2)).setImageResource(R.drawable.carta);
        }
        touch = 0;
        card1=-1;
        card2=-1;
    }

    private void exitGame() {
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Toast.makeText(getApplicationContext(), "partida finalizada", Toast.LENGTH_SHORT).show();
    }
}
