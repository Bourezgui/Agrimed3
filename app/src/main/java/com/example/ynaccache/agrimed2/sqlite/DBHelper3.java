package com.example.ynaccache.agrimed2.sqlite;

/**
 * Created by IT001 on 23-Jun-16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ynaccache.agrimed2.model.commande;
import com.example.ynaccache.agrimed2.model.details;

public class DBHelper3 extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 11;

    // Database Name
    private static final String DATABASE_NAME = "crud3.db";

    public DBHelper3(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + commande.TABLE  + "("
                + commande.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + commande.KEY_numclient + " TEXT, "
                + commande.KEY_nomclient + " TEXT, "
                + commande.KEY_numsite + " TEXT, "
                + commande.KEY_nomsite + " TEXT, "
                + commande.KEY_datecommande + " TEXT, "
                + commande.KEY_dateliv + " TEXT, "
                + commande.KEY_priorite + " TEXT, "
                + commande.KEY_observation + " TEXT, "
                + commande.KEY_annnee + " TEXT, "
                + commande.KEY_mois + " TEXT, "
                + commande.KEY_etat + " TEXT )";


        String CREATE_TABLE_STUDENT1 = "CREATE TABLE " + details.TABLE  + "("
                + details.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + details.KEY_num + " TEXT, "
                + details.KEY_name + " TEXT, "
                + details.KEY_quant + " TEXT, "
                + details.KEY_unite + " TEXT, "
                + details.KEY_numcommande + " TEXT )";
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_STUDENT1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + commande.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + details.TABLE);


        // Create tables again
        onCreate(db);

    }

}