package com.example.ynaccache.agrimed2.sqlite;

/**
 * Created by IT001 on 23-Jun-16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ynaccache.agrimed2.model.cli;

public class DBHelper1 extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "crud1.db";

    public DBHelper1(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + cli.TABLE  + "("
                + cli.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + cli.KEY_name + " TEXT, "
                + cli.KEY_num + " TEXT, "
                + cli.KEY_nomsite + " TEXT )";

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + cli.TABLE);

        // Create tables again
        onCreate(db);

    }

}