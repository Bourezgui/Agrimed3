package com.example.ynaccache.agrimed2.sqlite;

/**
 * Created by IT001 on 23-Jun-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ynaccache.agrimed2.model.cli;
import com.example.ynaccache.agrimed2.model.details;
import com.example.ynaccache.agrimed2.model.site;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientRepo {
    private DBHelperclient dbHelper;

    public ClientRepo(Context context) {
        dbHelper = new DBHelperclient(context);
    }

    public int insert(cli student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(cli.KEY_name, student.nomclien);
        values.put(cli.KEY_num, student.numclient);
        values.put(cli.KEY_nomsite, student.nomsite);



        // Inserting Row
        long student_Id = db.insert(cli.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public void deleteclient(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        // Inserting Row
        db.execSQL("delete from "+ cli.TABLE);
        db.close(); // Closing database connection
    }

   /* public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }*/

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

    public ArrayList<HashMap<String, String>> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * " +
                " FROM " + cli.TABLE
               ;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("nomclient", cursor.getString(cursor.getColumnIndex(cli.KEY_name)));
                student.put("numclient", cursor.getString(cursor.getColumnIndex(cli.KEY_num)));
                //student.put("numclient", cursor.getString(cursor.getColumnIndex(cli.KEY_num)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    /*public ArrayList<HashMap<String, String>> getStudentList(String site) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT * " +
                " FROM " + cli.TABLE
                + " WHERE " +
                cli.KEY_nomsite+ "=?";

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { site });
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("nomclient", cursor.getString(cursor.getColumnIndex(cli.KEY_name)));
                student.put("numclient", cursor.getString(cursor.getColumnIndex(cli.KEY_num)));
                //student.put("numclient", cursor.getString(cursor.getColumnIndex(cli.KEY_num)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }*/

   /* public Student getStudentById(int Id){
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