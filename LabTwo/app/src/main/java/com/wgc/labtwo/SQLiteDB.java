package com.wgc.labtwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDB extends SQLiteOpenHelper {
    private static SQLiteDB instance;
    SQLiteDatabase db;
    public static final String DB_NAME = "college_database";
    public static final String TABLE_NAME = "college_table";
    public static final String COL1 = "id";
    public static final String COL2 = "college_name";
    public static final String COL3 = "college_loc";
    public static final String COL4 = "SAT_score";

    public static SQLiteDB getSingleInstance(@Nullable Context context) {
        if(instance == null) {
            System.out.println("create instance");
          instance = new SQLiteDB(context);
        }
        return instance;
    }

    private SQLiteDB(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, "+ COL3 + " TEXT, " + COL4 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addCollege() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, "dummyName");
        contentValues.put(COL3, "dummyLoc");
        contentValues.put(COL4, 0);
        boolean res = db.insert(TABLE_NAME, null, contentValues) > 0;
        System.out.println("add dummy data res : " + res);
        return res;
    }

    public Cursor getAllCollege() {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
