package com.example.ynaccache.agrimed2.sqlite;

/**
 * Created by IT001 on 23-Jun-16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ynaccache.agrimed2.model.article;
import com.example.ynaccache.agrimed2.model.user;

public class DBHelper4 extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "crud4.db";

    public DBHelper4(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + user.TABLE  + "("
                + user.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + user.KEY_code+ " TEXT, "
                + user.KEY_nomuser + " TEXT, "
                + user.KEY_prenomuser + " TEXT, "
                + user.KEY_login + " TEXT, "
                + user.KEY_pass + " TEXT, "
                + user.KEY_etat  + " TEXT, "
                + user.KEY_numsite  + " TEXT, "
                + user.KEY_appareil + " TEXT, "
                + user.KEY_prefix + " TEXT, "
                + user.KEY_imei + " TEXT )";

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + article.TABLE);

        // Create tables again
        onCreate(db);

    }

}