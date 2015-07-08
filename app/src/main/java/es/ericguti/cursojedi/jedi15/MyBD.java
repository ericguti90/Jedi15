package es.ericguti.cursojedi.jedi15;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyBD extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "project";
    private static final String STATISTICS_TABLE_NAME_USER = "usuaris";
    private static final String STATISTICS_TABLE_NAME_RANKING = "ranking";
    private static final String STATISTICS_TABLE_CREATE_USER = "CREATE TABLE " + STATISTICS_TABLE_NAME_USER +
            " (user TEXT PRIMARY KEY, password TEXT, address TEXT, image TEXT)";
    private static final String STATISTICS_TABLE_CREATE_RANKING = "CREATE TABLE " + STATISTICS_TABLE_NAME_RANKING +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, points INTEGER)";

    MyBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STATISTICS_TABLE_CREATE_USER);
        db.execSQL(STATISTICS_TABLE_CREATE_RANKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STATISTICS_TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + STATISTICS_TABLE_NAME_RANKING);
        db.execSQL(STATISTICS_TABLE_CREATE_USER);
        db.execSQL(STATISTICS_TABLE_CREATE_RANKING);
    }
}
