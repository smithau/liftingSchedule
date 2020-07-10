package com.example.workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String TABLE_NAME = "Lifts_Table";
    private static final String COL0 = "ID";
    private static final String COL1 = "Lift";
    private static final String COL2 = "Day";
    private static final String COL3 = "Sets";
    private static final String COL4 = "Reps";
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
        //clearDB(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( "
                + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + COL1 + " TEXT , "
                + COL2 + " TEXT , "
                + COL3 + " TEXT , "
                + COL4 + " TEXT )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, String day, String set, String rep)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, day);
        contentValues.put(COL3, set);
        contentValues.put(COL4, rep);

        Log.d(TAG, "addData: Adding: " + name + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void clearDB(Context context)
    {
        context.deleteDatabase(TABLE_NAME);
    }
}
