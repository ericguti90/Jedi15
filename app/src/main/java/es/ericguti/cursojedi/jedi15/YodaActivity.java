package es.ericguti.cursojedi.jedi15;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;


public class YodaActivity extends ActionBarActivity {

    CircleProgressBar cpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoda);
        ImageView gif = (ImageView) findViewById(R.id.imageView57);
        cpb = (CircleProgressBar) findViewById(R.id.progressBar);
        cpb.onAnimationStart();
        Ion.with(this)
                .load("http://media1.giphy.com/media/6fScAIQR0P0xW/giphy.gif")
                .withBitmap()
                .fitCenter()
                .intoImageView(gif).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                cpb.setVisibility(View.INVISIBLE);
            }
        });
    }
}
