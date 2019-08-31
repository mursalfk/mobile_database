package com.example.lab14task1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static String DBNAME = "StudentsDB.db";
    private static int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DBNAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lab14task(_id INTEGER PRIMARY KEY AUTOINCREMENT, rollNumber INTEGER, name TEXT, marks INTEGER)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS lab14task");
        onCreate(db);
    }
}