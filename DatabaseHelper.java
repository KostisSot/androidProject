package com.example.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "texts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TEXT + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertText(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    // Επιστρέφει όλα τα κείμενα σε μορφή String (με αλλαγές γραμμής)
    public String getAllTexts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {
            String text = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT));
            builder.append(text).append("\n");
        }

        cursor.close();
        db.close();
        return builder.toString();
    }
    public String getLastText() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TEXT + " FROM " + TABLE_NAME +
                " ORDER BY " + COLUMN_ID + " DESC LIMIT 1", null);
        String lastText = "";

        if (cursor.moveToFirst()) {
            lastText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT));
        }

        cursor.close();
        db.close();
        return lastText;
    }


}
