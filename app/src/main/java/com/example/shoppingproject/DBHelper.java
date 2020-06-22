package com.example.shoppingproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    public DBHelper(Context context){
        super(context,"mydatabase",null,1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table users(ID integer primary key autoincrement, UserName text, Password text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insert(String name, String password){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put("UserName",name);
        contentValues.put("Password",password);
        long ins = db.insert("users",null,contentValues);
        if(ins==-1)
            return false;
        return true;
    }
    public boolean validateUser(String name, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select UserName, Password from users where UserName = ? and Password = ?", new String[]{name, password});
        if(cursor.getCount()>0)
            return true;
        return false;
    }
    //public boolean CheckUserName(String name){
        //SQLiteDatabase db=this.getReadableDatabase();
        //Cursor cursor = db.rawQuery("Select UserName from users where UserName = ?", new String[]{name});
        //if(cursor.getCount()>0)
            //return false;
        //return true;
    //}
}
