package es.ericguti.cursojedi.jedi15;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class PerfilActivity extends FragmentActivity implements View.OnClickListener, AddressNotification.Callback{
    TextView dir,name,points;
    ImageView img;
    List<Address> addressList;
    LocationManager locationManager;
    LocationListener locationListener;

    void loadInfo(){
        name.setText(getIntent().getExtras().getString("user"));
        MyBD bdUsers = new MyBD(this);
        SQLiteDatabase db = bdUsers.getWritableDatabase();
        if(db != null) {
            String[] arg = new String[]{getIntent().getExtras().getString("user")};
            Cursor c = db.rawQuery(" SELECT address,image FROM usuaris WHERE user=? ", arg);
            if (c.moveToFirst()) {
                if(c.getString(0).equals("")) dir.setText(R.string.direccion_tag);
                else dir.setText(c.getString(0));
                if(!c.getString(1).equals(""))Picasso.with(getApplicationContext()).load(c.getString(1)).error(R.drawable.icon_perfil).resize(100, 100).transform(new CircleTransform()).into(img);
            }
            c = db.rawQuery(" SELECT MIN(points) FROM ranking WHERE user=? ", arg);
            if (c.moveToFirst()) points.setText(c.getInt(0)+"");
            else points.setText(0);
        }
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        img = (ImageView) findViewById(R.id.imageView49);
        ImageView add = (ImageView) findViewById(R.id.imageView52);
        name = (TextView) findViewById(R.id.textView6);
        dir = (TextView) findViewById(R.id.textView7);
        points = (TextView) findViewById(R.id.textView9);
        img.setOnClickListener(this);
        add.setOnClickListener(this);
        loadInfo();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder gc = new Geocoder(getApplicationContext());
                try {
                    addressList = gc.getFromLocation(location.getLatitude(), location.getLongitude(),5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*for (int i = 0; i < addressList.size(); i++) {
                    if (i == 0) dir.setText("");
                    dir.setText((dir.getText() + "\n" + addressList.get(i).getAddressLine(0).toString()));
                }*/
                // Solo muestro la calle y no el resto de info como se hace en el for anterior
                if (addressList.size()>0) {
                    String street = addressList.get(0).getAddressLine(0).toString();
                    dir.setText(street);
                    locationManager.removeUpdates(locationListener);
                    //locationManager = null;
                    MyBD bdUsers = new MyBD(getApplicationContext());
                    SQLiteDatabase db = bdUsers.getWritableDatabase();
                    if(db != null) db.execSQL("UPDATE usuaris SET address='"+ street.toString() +"' WHERE user='"+ getIntent().getExtras().getString("user") +"'");
                    db.close();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }

    @Override
    public void searchGPS(){
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView49:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);
                break;
            case R.id.imageView52:
                AddressNotification dialogo = new AddressNotification();
                dialogo.show(getFragmentManager(),"dialog");
                break;
        }
    }

    public void onDialogPositiveClick(DialogFragment dialog) {
        // User pressed OK, so we need to grab the values from the
        // dialog's fields and apply them to the Views in the Main
        // Activity
        Dialog dialogView = dialog.getDialog();
        EditText eText = (EditText) dialogView.findViewById(R.id.editText3);
        TextView text = (TextView) findViewById(R.id.textView7);
        text.setText(eText.getText());
        MyBD bdUsers = new MyBD(this);
        SQLiteDatabase db = bdUsers.getWritableDatabase();
        if(db != null) {
            db.execSQL("UPDATE usuaris SET address='"+ eText.getText() +"' WHERE user='"+ getIntent().getExtras().getString("user") +"'");
        }
        db.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Picasso.with(getApplicationContext()).load(data.getDataString()).error(R.drawable.icon_perfil).resize(100, 100).transform(new CircleTransform()).into(img);
            MyBD bdUsers = new MyBD(this);
            SQLiteDatabase db = bdUsers.getWritableDatabase();
            if(db != null) db.execSQL("UPDATE usuaris SET image='"+ data.getDataString() +"' WHERE user='"+ getIntent().getExtras().getString("user") +"'");
            db.close();
        }
    }
}
