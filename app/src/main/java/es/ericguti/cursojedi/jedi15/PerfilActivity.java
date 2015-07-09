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
            //db.execSQL("INSERT INTO usuaris VALUES ('eric1','eric','plaza','img')");
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
                    locationManager = null;
                    //Log.v("LOG", ((Double) location.getLatitude()).toString());
                    MyBD bdUsers = new MyBD(getApplicationContext());
                    SQLiteDatabase db = bdUsers.getWritableDatabase();
                    if(db != null) {
                        //db.execSQL("INSERT INTO usuaris VALUES ('eric1','eric','plaza','img')");
                        //String[] arg = new String[]{data.getDataString(),getIntent().getExtras().getString("user")};
                        //db.rawQuery(" UPDATE usuaris SET image=? WHERE user=? ", arg);
                        db.execSQL("UPDATE usuaris SET address='"+ street.toString() +"' WHERE user='"+ getIntent().getExtras().getString("user") +"'");
                    }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
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


    /*public static Bitmap getRoundedCornerBitmap( Drawable drawable, boolean square) {
        int width = 0;
        int height = 0;

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap() ;

        if(square){
            if(bitmap.getWidth() < bitmap.getHeight()){
                width = bitmap.getWidth();
                height = bitmap.getWidth();
            } else {
                width = bitmap.getHeight();
                height = bitmap.getHeight();
            }
        } else {
            height = bitmap.getHeight();
            width = bitmap.getWidth();
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = 90;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView49:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);
                break;
            case R.id.imageView52:
                //FragmentManager fragmentManager = getFragmentManager();
                AddressNotification dialogo = new AddressNotification();
                dialogo.show(getFragmentManager(),"dialog");

                break;
        }
    }

    public void onDialogPositiveClick(DialogFragment dialog) {
        // User pressed OK, so we need to grab the values from the
        // dialog's fields and apply them to the Views in the Main
        // Activity

        // Start with the payment amount
        Dialog dialogView = dialog.getDialog();
        EditText eText = (EditText) dialogView.findViewById(R.id.editText3);
        TextView text = (TextView) findViewById(R.id.textView7);
        text.setText(eText.getText());
        MyBD bdUsers = new MyBD(this);
        SQLiteDatabase db = bdUsers.getWritableDatabase();
        if(db != null) {
            //db.execSQL("INSERT INTO usuaris VALUES ('eric1','eric','plaza','img')");
            //String[] arg = new String[]{data.getDataString(),getIntent().getExtras().getString("user")};
            //db.rawQuery(" UPDATE usuaris SET image=? WHERE user=? ", arg);
            db.execSQL("UPDATE usuaris SET address='"+ eText.getText() +"' WHERE user='"+ getIntent().getExtras().getString("user") +"'");
        }
        db.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            /*Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //img.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/
            Picasso.with(getApplicationContext()).load(data.getDataString()).error(R.drawable.icon_perfil).resize(100, 100).transform(new CircleTransform()).into(img);
            MyBD bdUsers = new MyBD(this);
            SQLiteDatabase db = bdUsers.getWritableDatabase();
            if(db != null) {
                //db.execSQL("INSERT INTO usuaris VALUES ('eric1','eric','plaza','img')");
                //String[] arg = new String[]{data.getDataString(),getIntent().getExtras().getString("user")};
                //db.rawQuery(" UPDATE usuaris SET image=? WHERE user=? ", arg);
                db.execSQL("UPDATE usuaris SET image='"+ data.getDataString() +"' WHERE user='"+ getIntent().getExtras().getString("user") +"'");
            }
            db.close();
            //Picasso.with(getApplicationContext()).loa
        }
    }


}
