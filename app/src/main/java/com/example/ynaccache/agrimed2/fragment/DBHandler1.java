package com.example.ynaccache.agrimed2.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler1 extends SQLiteOpenHelper {
    private static final String DB_NAME = "commande2";
    private static final int DB_VERSION = 1;
    private static final String TABLE_ENTETE= "entete";
    private static final String TABLE_PRENOM = "record2";

    private static final String ID_ENTETE= "id_client";
    private static final String NOM8CLIENT = "nom_client";
    private static final String DATE_COM = "date_com";
    private static final String ID = "id_client";
    private static final String NAME = "name";

    private static final String password_COL = "password";
    public DBHandler1(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ENTETE +
                " (" + ID_ENTETE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOM8CLIENT + " TEXT  )";
        /*String query2 = "CREATE TABLE " + TABLE_PRENOM +
                " (" + ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT  )";*/
        db.execSQL(query);
        //db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTETE);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRENOM);

        // Create table again
        onCreate(db);
    }

    public void insertRecord(String name,String datecom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NOM8CLIENT, name);
        values.put(NOM8CLIENT, datecom);

        db.insert(TABLE_ENTETE, null, values);
        db.close();
    }
    public void insertRecord2(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, name);

        db.insert(TABLE_PRENOM, null, values);
        db.close();
    }

    public String getRecords() {
        String query = "SELECT * FROM " + TABLE_ENTETE;
        String result = "";





        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            result += cursor.getString(0) + " " + cursor.getString(1) + "\n";
            cursor.moveToNext();
        }

        db.close();
        return result;
    }

    public String getRecords2() {
        String query = "SELECT * FROM " + TABLE_PRENOM;
        String result = "";





        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            result += cursor.getString(0) + " " + cursor.getString(1) + "\n";
            cursor.moveToNext();
        }

        db.close();
        return result;
    }

    public String getOneRecord(String id) {
        String query = "SELECT * FROM " + TABLE_ENTETE + " where id_client ="+id;
        String result = "";





        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            result += cursor.getString(0) + " " + cursor.getString(1) + "\n";
            cursor.moveToNext();
        }

        db.close();
        return result;
    }

    /*public void updateRecord(String id_client, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);

        db.update(TABLE_NAME, values, "id_client=?", new String[]{id_client});
        db.close();
    }

    public void deleteRecord(String id_client) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id_client=?", new String[]{id_client});

        db.close();
    }*/
}