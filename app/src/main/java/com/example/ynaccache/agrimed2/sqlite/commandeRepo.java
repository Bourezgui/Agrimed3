package com.example.ynaccache.agrimed2.sqlite;

/**
 * Created by IT001 on 23-Jun-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ynaccache.agrimed2.model.article;
import com.example.ynaccache.agrimed2.model.commande;
import com.example.ynaccache.agrimed2.model.details;
import com.example.ynaccache.agrimed2.other.util;

import java.util.ArrayList;
import java.util.HashMap;

public class commandeRepo {
    private DBHelper3 dbHelper;

    public commandeRepo(Context context) {
        dbHelper = new DBHelper3(context);
    }

    public long insert(commande student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(commande.KEY_numclient, student.numclient);
        values.put(commande.KEY_nomclient,student.nomclient);
        values.put(commande.KEY_numsite, student.numsite);
        values.put(commande.KEY_nomsite, student.nomsite);
        values.put(commande.KEY_datecommande, student.datecommande);
        values.put(commande.KEY_dateliv, student.dateliv);
        values.put(commande.KEY_priorite, student.priorite);
        values.put(commande.KEY_observation, student.observation);
        values.put(commande.KEY_annnee, student.anneee);
        values.put(commande.KEY_mois, student.mois);


        values.put(commande.KEY_etat, student.etat);











        // Inserting Row
        long student_Id = db.insert(commande.TABLE, null, values);
        db.close(); // Closing database connection
        return  student_Id;
    }

    public long insert1(details student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(details.KEY_num, student.numarticle);
        values.put(details.KEY_name,student.nomarticle);
        values.put(details.KEY_quant, student.quantite);
        values.put(details.KEY_quant, student.quantite);
        values.put(details.KEY_unite, student.unite);
        values.put(details.KEY_numcommande, student.numcommande);




        // Inserting Row
        long student_Id = db.insert(details.TABLE, null, values);
        db.close(); // Closing database connection
        return  student_Id;
    }

   /* public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }*/

   public void update(commande student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(commande.KEY_etat, student.etat);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(commande.TABLE, values, commande.KEY_ID + "= ?", new String[] { String.valueOf(student.commande_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT *  " +
                " FROM " + commande.TABLE +"  ORDER BY id DESC"

                ;



        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", util.convertor(cursor.getString(cursor.getColumnIndex(commande.KEY_ID)),5));
                student.put("id1", cursor.getString(cursor.getColumnIndex(commande.KEY_ID)));

                student.put("nomclient", cursor.getString(cursor.getColumnIndex(commande.KEY_nomclient)));
                student.put("numclient", cursor.getString(cursor.getColumnIndex(commande.KEY_numclient)));
                student.put("nomsite", cursor.getString(cursor.getColumnIndex(commande.KEY_nomsite)));
                student.put("nomsite", cursor.getString(cursor.getColumnIndex(commande.KEY_nomsite)));
                student.put("datecommande", cursor.getString(cursor.getColumnIndex(commande.KEY_datecommande)));
                student.put("dateliv", cursor.getString(cursor.getColumnIndex(commande.KEY_dateliv)));
                student.put("priorite", cursor.getString(cursor.getColumnIndex(commande.KEY_priorite)));
                student.put("observation", cursor.getString(cursor.getColumnIndex(commande.KEY_observation)));



               // student.put("prefix", cursor.getString(cursor.getColumnIndex(commande.KEY_prefix)));


                student.put("mois", cursor.getString(cursor.getColumnIndex(commande.KEY_mois)));
                student.put("anneee", cursor.getString(cursor.getColumnIndex(commande.KEY_annnee)));
                student.put("numsite", cursor.getString(cursor.getColumnIndex(commande.KEY_numsite)));
                student.put("etat", cursor.getString(cursor.getColumnIndex(commande.KEY_etat)));


                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }
    public ArrayList<HashMap<String, String>> getStudentList1(String etat) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT *  " +
                " FROM " + commande.TABLE
                + " WHERE " +
                commande.KEY_etat+ "=?";

                ;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { etat });
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(commande.KEY_ID)));
                student.put("nomclient", cursor.getString(cursor.getColumnIndex(commande.KEY_nomclient)));
                student.put("numclient", cursor.getString(cursor.getColumnIndex(commande.KEY_numclient)));
                student.put("nomsite", cursor.getString(cursor.getColumnIndex(commande.KEY_nomsite)));
                student.put("nomsite", cursor.getString(cursor.getColumnIndex(commande.KEY_nomsite)));
                student.put("datecommande", cursor.getString(cursor.getColumnIndex(commande.KEY_datecommande)));
                student.put("dateliv", cursor.getString(cursor.getColumnIndex(commande.KEY_dateliv)));
                student.put("priorite", cursor.getString(cursor.getColumnIndex(commande.KEY_priorite)));
                student.put("observation", cursor.getString(cursor.getColumnIndex(commande.KEY_observation)));



                // student.put("prefix", cursor.getString(cursor.getColumnIndex(commande.KEY_prefix)));


                student.put("mois", cursor.getString(cursor.getColumnIndex(commande.KEY_mois)));
                student.put("anneee", cursor.getString(cursor.getColumnIndex(commande.KEY_annnee)));
                student.put("numsite", cursor.getString(cursor.getColumnIndex(commande.KEY_numsite)));
                student.put("etat", cursor.getString(cursor.getColumnIndex(commande.KEY_etat)));


                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }
    public ArrayList<HashMap<String, String>> getStudentList2(String numcommande) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  * "  +
                " FROM " + details.TABLE
                + " WHERE " +
                details.KEY_numcommande+ "=?";

        ;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { numcommande });
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("numcommande", cursor.getString(cursor.getColumnIndex(details.KEY_numcommande)));
                student.put("nomarticle", cursor.getString(cursor.getColumnIndex(details.KEY_name)));
                student.put("numarticle", cursor.getString(cursor.getColumnIndex(details.KEY_num)));

                student.put("quantite", cursor.getString(cursor.getColumnIndex(details.KEY_quant)));
                student.put("unite", cursor.getString(cursor.getColumnIndex(details.KEY_unite)));

                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }
    public ArrayList<HashMap<String, String>> getStudentList3() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT *  " +
                " FROM " + commande.TABLE +"  ORDER BY id DESC LIMIT 1"
                ;



        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", util.convertor(cursor.getString(cursor.getColumnIndex(commande.KEY_ID)),5));
                student.put("id1", cursor.getString(cursor.getColumnIndex(commande.KEY_ID)));

                student.put("nomclient", cursor.getString(cursor.getColumnIndex(commande.KEY_nomclient)));
                student.put("numclient", cursor.getString(cursor.getColumnIndex(commande.KEY_numclient)));
                student.put("nomsite", cursor.getString(cursor.getColumnIndex(commande.KEY_nomsite)));
                student.put("nomsite", cursor.getString(cursor.getColumnIndex(commande.KEY_nomsite)));
                student.put("datecommande", cursor.getString(cursor.getColumnIndex(commande.KEY_datecommande)));
                student.put("dateliv", cursor.getString(cursor.getColumnIndex(commande.KEY_dateliv)));
                student.put("priorite", cursor.getString(cursor.getColumnIndex(commande.KEY_priorite)));
                student.put("observation", cursor.getString(cursor.getColumnIndex(commande.KEY_observation)));



                // student.put("prefix", cursor.getString(cursor.getColumnIndex(commande.KEY_prefix)));


                student.put("mois", cursor.getString(cursor.getColumnIndex(commande.KEY_mois)));
                student.put("anneee", cursor.getString(cursor.getColumnIndex(commande.KEY_annnee)));
                student.put("numsite", cursor.getString(cursor.getColumnIndex(commande.KEY_numsite)));
                student.put("etat", cursor.getString(cursor.getColumnIndex(commande.KEY_etat)));


                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    /*public Student getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_email + "," +
                Student.KEY_age +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Student student = new Student();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.email  =cursor.getString(cursor.getColumnIndex(Student.KEY_email));
                student.age =cursor.getInt(cursor.getColumnIndex(Student.KEY_age));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }*/

}