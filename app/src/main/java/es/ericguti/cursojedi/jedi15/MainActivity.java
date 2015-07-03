package es.ericguti.cursojedi.jedi15;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    TextView user, pass;

    public void loadLocale()
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    void updateTexts(){
        user.setText(R.string.usuario_tag);
        pass.setText(R.string.contra_tag);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase("")) return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    public void saveLocale(String lang)
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.imageView2:
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView3:
                changeLang("es");
                break;
            case R.id.imageView4:
                changeLang("ca");
                break;
            case R.id.imageView5:
                changeLang("en");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (TextView) findViewById(R.id.textView2);
        pass = (TextView) findViewById(R.id.textView3);
        ImageView enter = (ImageView) findViewById(R.id.imageView2);
        ImageView esp = (ImageView) findViewById(R.id.imageView3);
        ImageView cat = (ImageView) findViewById(R.id.imageView4);
        ImageView eng = (ImageView) findViewById(R.id.imageView5);
        enter.setOnClickListener(this);
        esp.setOnClickListener(this);
        cat.setOnClickListener(this);
        eng.setOnClickListener(this);
        loadLocale();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
