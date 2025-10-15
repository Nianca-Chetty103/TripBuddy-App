package com.example.tripbuddy20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MemoryDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memoryDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "uploads";

    private static final String COL_ID = "id";
    private static final String COL_IMAGE = "imageUri";
    private static final String COL_AUDIO = "audioUri";
    private static final String COL_TIMESTAMP = "timestamp";

    public MemoryDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_IMAGE + " TEXT, " +
                COL_AUDIO + " TEXT, " +
                COL_TIMESTAMP + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        if(oldVersion < 2){
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_TIMESTAMP + " TEXT");
        }
        onCreate(db);
    }

    // Insert record
    public  boolean insertMemory(String imageUri, String audioUri, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("imageUri", imageUri);
        values.put("audioUri", audioUri);
        values.put("timestamp", timestamp);

        long result = db.insert("uploads", null, values);
        db.close();

        return result != -1;
    }

    // Get all records
    public Cursor getAllMemories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " DESC", null);
    }
}
