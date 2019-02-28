package com.example.ynaccache.agrimed2.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "spinnerExample";
 
    // Labels table name
    private static final String TABLE_LABELS = "labels";
    private static final String TABLE_ARTICLE = "article";


    // Labels Table Columns names
    private static final String KEY_ID = "id_client";
    private static final String KEY_NAME = "name";
    private static final String KEY_IDARTICLE = "id_article";
    private static final String KEY_NAMEARTICLE = "name_article";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// Category table create query
    	String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_LABELS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
        String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLE + "("
                + KEY_IDARTICLE + " INTEGER PRIMARY KEY," + KEY_NAMEARTICLE + " TEXT)";
    	db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_ARTICLE_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LABELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
 
        // Create tables again
        onCreate(db);
    }
    
    /**
     * Inserting new lable into lables table
     * */
    public void insertLabel(String label){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(KEY_NAME,label);

    	// Inserting Row
        db.insert(TABLE_LABELS, null, values);

        db.close(); // Closing database connection
    }
    public void insertArticlee(String article){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMEARTICLE,article);

        // Inserting Row
        db.insert(TABLE_ARTICLE, null, values);

        db.close(); // Closing database connection
    }
    public void deleteLabel(){
        SQLiteDatabase db = this.getWritableDatabase();



        // Inserting Row
        db.execSQL("delete from "+ TABLE_LABELS);
        db.close(); // Closing database connection
    }
    public void deleteArticle(){
        SQLiteDatabase db = this.getWritableDatabase();



        // Inserting Row
        db.execSQL("delete from "+ TABLE_ARTICLE);
        db.close(); // Closing database connection
    }
    
    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllLabels(){
    	List<String> labels = new ArrayList<String>();
    	
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS;
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        
        // closing connection
        cursor.close();
        db.close();
    	
    	// returning lables
    	return labels;
    }

    public List<String> getAllArticle(){
        List<String> article = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ARTICLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                article.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return article;
    }
}
