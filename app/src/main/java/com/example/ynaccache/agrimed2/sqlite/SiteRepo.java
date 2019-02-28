package com.example.ynaccache.agrimed2.sqlite;

/**
 * Created by IT001 on 23-Jun-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ynaccache.agrimed2.model.site;
import com.example.ynaccache.agrimed2.model.user;

import java.util.ArrayList;
import java.util.HashMap;

public class SiteRepo {
    private DBHelper dbHelper;

    public SiteRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(site student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(site.KEY_name, student.nomsite);
        values.put(site.KEY_num,student.numsite);

        // Inserting Row
        long student_Id = db.insert(site.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

   public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(site.TABLE, site.KEY_ID , new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }
    public void deleteSitel(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        // Inserting Row
        db.execSQL("delete from "+ site.TABLE);
        db.close(); // Closing database connection
    }

    /*public void update(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_age, student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name, student.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
        db.close(); // Closing database connection
    }*/
    public void deleteuser(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        // Inserting Row
        db.execSQL("delete from "+ site.TABLE);
        db.close(); // Closing database connection
    }
    public ArrayList<HashMap<String, String>> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                site.KEY_ID + "," +
                site.KEY_name + "," +
                site.KEY_num +
                " FROM " + site.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("nomsite", cursor.getString(cursor.getColumnIndex(site.KEY_name)));
                student.put("numsite", cursor.getString(cursor.getColumnIndex(site.KEY_num)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    public site getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                site.KEY_ID + "," +
                site.KEY_name + "," +
                site.KEY_num +
                " FROM " + site.TABLE
                + " WHERE " +
                site.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        site student = new site();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.site_ID =cursor.getInt(cursor.getColumnIndex(site.KEY_ID));
                student.nomsite =cursor.getString(cursor.getColumnIndex(site.KEY_name));
                student.numsite  =cursor.getString(cursor.getColumnIndex(site.KEY_num));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }

}