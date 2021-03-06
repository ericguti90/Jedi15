package es.ericguti.cursojedi.jedi15;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "fSzqFJ23GN4Aj3Of1yFrsERTC";
    private static final String TWITTER_SECRET = "cwmp6K6eJTSKEaoPAluLnyUoGnkI2U650VnI83Had4WuaTRqy0";
    private TwitterLoginButton loginButton;

    TextView user, pass;
    EditText userIN, passIN;

    public void loadLocale()
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void checkUser(){
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String user = prefs.getString("user", "");
        if(!user.equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
            finish();
        }
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

    public void saveUser(String user)
    {
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user", user);
        editor.apply();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.imageView2:
                 login();
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

    private void login() {
        MyBD bdUsers = new MyBD(this);
        SQLiteDatabase db = bdUsers.getWritableDatabase();
        if(db != null){
            //db.execSQL("INSERT INTO usuaris VALUES ('eric1','eric','plaza','img')");
            String[] arg = new String[] {userIN.getText().toString()};
            Cursor c = db.rawQuery(" SELECT user,password FROM usuaris WHERE user=? ", arg);
            if (c.moveToFirst()) {
                String password = c.getString(1);
                if (password.equals(passIN.getText().toString())) {
                    db.close();
                    saveUser(userIN.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    intent.putExtra("user",userIN.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    AlertaLogin dialogo = new AlertaLogin();
                    dialogo.show(fragmentManager, "tagAlerta");
                }
            }
            else {
                //crear usuario
                if(!userIN.getText().toString().equals("")) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DialogoConfirmacion dialogo = new DialogoConfirmacion();
                    Bundle bundle = new Bundle();
                    bundle.putString("user", userIN.getText().toString());
                    bundle.putString("pass", passIN.getText().toString());
                    dialogo.setArguments(bundle); //para pasar argumentos al dialogo
                    dialogo.show(fragmentManager, "tagAlerta");
                }
            }
            c.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        checkUser();
        user = (TextView) findViewById(R.id.textView2);
        pass = (TextView) findViewById(R.id.textView3);
        userIN = (EditText) findViewById(R.id.editText);
        passIN = (EditText) findViewById(R.id.editText2);
        ImageView enter = (ImageView) findViewById(R.id.imageView2);
        ImageView esp = (ImageView) findViewById(R.id.imageView3);
        ImageView cat = (ImageView) findViewById(R.id.imageView4);
        ImageView eng = (ImageView) findViewById(R.id.imageView5);
        enter.setOnClickListener(this);
        esp.setOnClickListener(this);
        cat.setOnClickListener(this);
        eng.setOnClickListener(this);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls

                MyBD bdUsers = new MyBD(getApplicationContext());
                SQLiteDatabase db = bdUsers.getWritableDatabase();
                if(db != null){
                    //db.execSQL("INSERT INTO usuaris VALUES ('eric1','eric','plaza','img')");
                    String[] arg = new String[] {result.data.getUserName()};
                    Cursor c = db.rawQuery(" SELECT user FROM usuaris WHERE user=? ", arg);
                    if (c.moveToFirst()) {
                            db.close();
                            saveUser(result.data.getUserName());
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            intent.putExtra("user",result.data.getUserName().toString());
                            startActivity(intent);
                            finish();
                    }
                    else {
                        //crear usuario
                        db.execSQL("INSERT INTO usuaris VALUES ('" + result.data.getUserName() + "','" + result.data.getUserId() + "','','')");
                        db.close();
                        saveUser(result.data.getUserName());
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.putExtra("user", result.data.getUserName().toString());
                        startActivity(intent);
                        finish();
                    }
                    c.close();
                }

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });
        loadLocale();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user", userIN.getText().toString());
        outState.putString("pass", passIN.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userIN.setText(savedInstanceState.getString("user"));
        passIN.setText(savedInstanceState.getString("pass"));
    }
}
