package es.ericguti.cursojedi.jedi15;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by inlab on 03/07/2015.
 */
public class MyBD extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "project";
    private static final String STATISTICS_TABLE_NAME = "usuaris";
    private static final String STATISTICS_TABLE_CREATE = "CREATE TABLE" + STATISTICS_TABLE_NAME +
            "(user TEXT PRIMARY KEY, password TEXT)";

    MyBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STATISTICS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + STATISTICS_TABLE_NAME);
        db.execSQL(STATISTICS_TABLE_CREATE);
    }
}
