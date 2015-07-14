package es.ericguti.cursojedi.jedi15;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


public class MenuActivity extends ActionBarActivity implements View.OnClickListener {
    @Override
    public void onClick(View v){
        Intent intent = null;
        String user;
        switch (v.getId()) {
            case R.id.imageView10:
                intent = new Intent(getApplicationContext(), CalculadoraActivity.class);
                break;
            case R.id.imageView7:
                user = getIntent().getExtras().getString("user");
                intent = new Intent(getApplicationContext(), memoryActivity.class);
                intent.putExtra("user",user);
                break;
            case R.id.imageView8:
                user = getIntent().getExtras().getString("user");
                intent = new Intent(getApplicationContext(), PerfilActivity.class);
                intent.putExtra("user",user);
                break;
            case R.id.imageView9:
                intent = new Intent(getApplicationContext(), RankingActivity.class);
                break;
            case R.id.imageView11:
                intent = new Intent(getApplicationContext(), MusicActivity.class);
                break;
        }
        if(!intent.equals(null))startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ImageView calc = (ImageView) findViewById(R.id.imageView10);
        ImageView game = (ImageView) findViewById(R.id.imageView7);
        ImageView rank = (ImageView) findViewById(R.id.imageView9);
        ImageView user = (ImageView) findViewById(R.id.imageView8);
        ImageView play = (ImageView) findViewById(R.id.imageView11);
        calc.setOnClickListener(this);
        game.setOnClickListener(this);
        rank.setOnClickListener(this);
        user.setOnClickListener(this);
        play.setOnClickListener(this);
        createB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
            String langPref = "user";
            SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(langPref);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void createB(){

        final View actionB = findViewById(R.id.action_b);

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        //actionC.setTitle("Hide/Show Action above");
        //actionC.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //    public void onClick(View v) {
        //        actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        //    }
        //});
        //((FloatingActionsMenu) findViewById(R.id.multiple_actions)).addButton(actionC);




        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("Action A clicked");
            }
        });
    }
}
