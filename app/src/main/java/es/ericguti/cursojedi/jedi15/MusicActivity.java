package es.ericguti.cursojedi.jedi15;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MusicActivity extends ActionBarActivity{
    TextView text;
    int[] viewCoords = new int[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        final ImageView play = (ImageView) findViewById(R.id.imageView56);
        text = (TextView) findViewById(R.id.textView11);
        ListView music = (ListView) findViewById(R.id.listView);
        play.getLocationOnScreen(viewCoords);
        ViewTreeObserver vto = play.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                play.getViewTreeObserver().removeOnPreDrawListener(this);
                double finalHeight = play.getMeasuredHeight();
                double finalWidth = play.getMeasuredWidth();
                text.setText("Height: " + finalHeight + " Width: " + finalWidth);
                return true;
            }
        });

                //text.setText(play.getMeasuredWidth() + " " + play.getMeasuredHeight());
        play.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch (View v, MotionEvent event) {
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();
                int imageX = touchX - viewCoords[0]; // viewCoords[0] is the X coordinate
                int imageY = touchY - viewCoords[1]; // viewCoords[1] is the y coordinate
                text.setText(imageX + " - " + imageY);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
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
