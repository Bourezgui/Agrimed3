package com.example.ynaccache.agrimed2.sqlite;

/**
 * Created by IT001 on 23-Jun-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ynaccache.agrimed2.model.cli;
import com.example.ynaccache.agrimed2.model.user;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepo {
    private DBHelper4 dbHelper;

    public UserRepo(Context context) {
        dbHelper = new DBHelper4(context);
    }

    public int insert(user student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(user.KEY_code, student.code);
        values.put(user.KEY_nomuser,student.nomuser);
        values.put(user.KEY_prenomuser, student.prenomuser);
        values.put(user.KEY_login, student.login);
        values.put(user.KEY_pass, student.pass);
        values.put(user.KEY_etat, student.etat);
        values.put(user.KEY_numsite, student.numsite);
        values.put(user.KEY_appareil, student.nomappareil);
        values.put(user.KEY_prefix, student.prefix);
        values.put(user.KEY_imei, student.imei);



        // Inserting Row
        long student_Id = db.insert(user.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    /*public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(user.TABLE, user.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }*/

   /* public void update(Student student) {

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

                " FROM " + user.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(user.KEY_ID)));
                student.put("code", cursor.getString(cursor.getColumnIndex(user.KEY_code)));
                student.put("nomuser", cursor.getString(cursor.getColumnIndex(user.KEY_nomuser)));
                student.put("prenomuser", cursor.getString(cursor.getColumnIndex(user.KEY_prenomuser)));
                student.put("login", cursor.getString(cursor.getColumnIndex(user.KEY_login)));
                student.put("pass", cursor.getString(cursor.getColumnIndex(user.KEY_pass)));
                student.put("etat", cursor.getString(cursor.getColumnIndex(user.KEY_etat)));
                student.put("numsite", cursor.getString(cursor.getColumnIndex(user.KEY_numsite)));
                student.put("nomappareil", cursor.getString(cursor.getColumnIndex(user.KEY_appareil)));
                student.put("imei", cursor.getString(cursor.getColumnIndex(user.KEY_imei)));
                student.put("prefix", cursor.getString(cursor.getColumnIndex(user.KEY_prefix)));

                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }
    public void deleteuser(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        // Inserting Row
        db.execSQL("delete from "+ user.TABLE);
        db.close(); // Closing database connection
    }

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