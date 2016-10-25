package com.example.prashanthmudhelli.datastorage;

/**
 * Created by prashanth.mudhelli on 3/9/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataController {
    public static final String COL1 = "BookName";
    public static final String COL2 = "BookAuthor";
    public static final String COL3 = "BookDesc";
    public static final String COL4 = "TimeStamp";
    public static final String TABLE_NAME = "Book_Details";
    public static final String DATABASE_NAME = "DataStorage.db";
    public static final int DATABASE_VERSION = 1;

    public static String TABLE_CREATE = "create table " +TABLE_NAME +" (" +COL1 +" TEXT, " +COL2 +" TEXT, " +COL3 +" TEXT, " +COL4 +" TEXT);";

    public static DataBaseHelper dbHelper;
    Context context;
    public static SQLiteDatabase db;

    public DataController(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(context);
    }

    public DataController open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public static SQLiteDatabase read() {
        db = dbHelper.getReadableDatabase();
        return db;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String name, String author, String desc, String time) {
        open();
        ContentValues content = new ContentValues();
        content.put(COL1, name);
        content.put(COL2, author);
        content.put(COL3, desc);
        content.put(COL4, time);
        return db.insert(TABLE_NAME, null, content);
    }

    public Cursor retrieve(String[] reqCols, String sort) {
        read();
        return db.query(TABLE_NAME, reqCols, null, null, null, null, sort);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLE_CREATE);
            }
            catch(SQLiteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);
        }
    }
}