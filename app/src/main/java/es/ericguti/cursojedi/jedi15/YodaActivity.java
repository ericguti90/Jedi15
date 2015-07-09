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
        Ion.with(this)
                .load("http://media1.giphy.com/media/6fScAIQR0P0xW/giphy.gif")
                .withBitmap()
                .fitCenter()
                .intoImageView(gif);
    }
}
