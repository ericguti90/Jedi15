package es.ericguti.cursojedi.jedi15;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;


public class YodaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoda);
        ImageView gif = (ImageView) findViewById(R.id.imageView57);
        //Picasso.with(this).load("http://media1.giphy.com/media/6fScAIQR0P0xW/giphy.gif").into(video);
        Ion.with(this)
                .load("http://media1.giphy.com/media/6fScAIQR0P0xW/giphy.gif")
                .withBitmap()
                .fitCenter()
                .intoImageView(gif);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_yoda, menu);
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
