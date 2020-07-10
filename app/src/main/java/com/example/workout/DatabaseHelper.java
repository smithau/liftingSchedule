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
    private static final String COL5 = "Weight";
    private static final String COL6 = "Finished";
    private static final String COL7 = "Notes";

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
                + COL2 + " INTEGER , "
                + COL3 + " INTEGER , "
                + COL4 + " INTEGER , "
                + COL5 + " INTEGER , "
                + COL6 + " INTEGER , "
                + COL7 + " TEXT ) ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Creates a new row in the database
     * @param name
     * @param day
     * @param set
     * @param rep
     * @param weight
     * @param notes
     * @return False if row was not created
     */
    public boolean addData(String name, int day, int set, int rep, int weight,String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, day);
        contentValues.put(COL3, set);
        contentValues.put(COL4, rep);
        contentValues.put(COL5, weight);
        contentValues.put(COL6, 0);
        contentValues.put(COL7, notes);

        Log.d(TAG, "addData: Adding: " + name + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    /**
     * Gets information from the database
     * @return Returns all data in the database
     */
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name of the lift
     * @param newName
     * @param oldName
     * @param id
     */
    public void editLift(String newName, String oldName, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1
                + " = '" + newName + "' WHERE " + COL0 + " = '" + id
                + "' AND " + COL1 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    /**
     * Updates the day of the lift
     * @param newDay
     * @param oldDay
     * @param id
     */
    public void editDay(int newDay, int oldDay, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2
                + " = '" + newDay + "' WHERE " + COL0 + " = '" + id
                + "' AND " + COL2 + " = '" + oldDay + "'";
        db.execSQL(query);
    }

    /**
     * Updates how many sets
     * @param newSet
     * @param oldSet
     * @param id
     */
    public void editSets(int newSet, int oldSet, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3
                + " = '" + newSet + "' WHERE " + COL0 + " = '" + id
                + "' AND " + COL3 + " = '" + oldSet + "'";
        db.execSQL(query);
    }

    /**
     * Updates how many reps
     * @param newRep
     * @param oldRep
     * @param id
     */
    public void editRep(int newRep, int oldRep, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4
                + " = '" + newRep + "' WHERE " + COL0 + " = '" + id
                + "' AND " + COL4 + " = '" + oldRep + "'";
        db.execSQL(query);
    }

    /**
     * Updates the weight
     * @param newWeight
     * @param oldWeight
     * @param id
     */
    public void editWeight(int newWeight, int oldWeight, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5
                + " = '" + newWeight + "' WHERE " + COL0 + " = '" + id
                + "' AND " + COL5 + " = '" + oldWeight + "'";
        db.execSQL(query);
    }

    /**
     * Updates the notes
     * @param newNote
     * @param oldNote
     * @param id
     */
    public void editNotes(String newNote, String oldNote, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL7
                + " = '" + newNote + "' WHERE " + COL0 + " = '" + id
                + "' AND " + COL7 + " = '" + oldNote + "'";
        db.execSQL(query);
    }

    /**
     * Deletes a row
     * @param id
     */
    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = '" + id + "'";
        db.execSQL(query);
    }

    /**
     * Clears the database
     * @param context
     */
    public void clearDB(Context context)
    {
        context.deleteDatabase(TABLE_NAME);
    }
}