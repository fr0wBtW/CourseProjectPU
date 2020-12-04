package com.example.courseprojectpu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.Transliterator;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public abstract class DataBase extends AppCompatActivity {
    protected void initDb() throws SQLException {
        SQLiteDatabase db=
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath()+"/position.db",
                        null
                );
        String createQ="CREATE TABLE if not exists Position( " +
                "ID integer PRIMARY KEY AUTOINCREMENT, " +
                "Name text not null, " +
                "Position text not null, " +
                "Describe text not null,"+
                "Tel text not null, " +
                "Email text not null, " +
                "unique(Tel, Email) " +
                "); ";
        db.execSQL(createQ);
        db.close();

    }

    protected void ExecSQL(String SQL, Object[] args, OnQuerySuccess success) throws SQLException{
        SQLiteDatabase db=
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath()+"/position.db",
                        null
                );

        db.execSQL(SQL, args);
        success.OnSuccess();
        db.close();

    }

    public void SelectSQL(String SQL, String[] args, OnSelectElement iterate)
            throws Exception
    {
        SQLiteDatabase db=
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath()+"/position.db",
                        null
                );

        Cursor cursor=db.rawQuery(SQL, args);
        while (cursor.moveToNext()){
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String Name=cursor.getString(cursor.getColumnIndex("Name"));
            String Position=cursor.getString(cursor.getColumnIndex("Position"));
            String Describe =cursor.getString(cursor.getColumnIndex("Describe"));
            String Tel=cursor.getString(cursor.getColumnIndex("Tel"));
            String Email=cursor.getString(cursor.getColumnIndex("Email"));
            iterate.OnElementIterate(Name, Position, Describe, Tel, Email, ID);
        }
        db.close();

    }

    protected interface OnQuerySuccess{

        public void OnSuccess();

    }

    protected interface OnSelectElement{
        public void OnElementIterate(String Name, String Position, String Describe, String tel, String Email, String ID);
    }
}
