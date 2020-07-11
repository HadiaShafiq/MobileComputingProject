package com.example.shoppingproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    public DBHelper(Context context){
        super(context,"mydatabase",null,2);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table users(ID integer primary key autoincrement, UserName text, Password text )");
        db.execSQL("Create table Category(CategoryID integer primary key autoincrement, CategoryName text, CategoryImage BLOB )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users" );
        db.execSQL("DROP TABLE IF EXISTS Category");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    public int getUserId(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select ID from Category",null);
        int id =cursor.getInt(0);
        return id;
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



    public boolean insertCategory(String CategoryName, byte[] imageBytes){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put("CategoryName",CategoryName);
        contentValues.put("CategoryImage",imageBytes);
        long ins = db.insert("Category",null,contentValues);
        if(ins==-1)
            return false;
        return true;
    }
    public List<Category> getCategories(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Category",null);
        List<Category> categoryList = new ArrayList<Category>();
        Category category =new Category();
        if(cursor.moveToFirst()){
            do{
                category.setCategoryId(cursor.getInt(0));
                category.setcategoryName(cursor.getString(1));
                category.setcategoryImg(cursor.getBlob(2));
                categoryList.add(category);
            }while (cursor.moveToNext());
        }
        return categoryList;
    }

    public boolean CheckUserName(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select UserName from users where UserName = ?", new String[]{name});
        if(cursor.getCount()>0)
            return false;
        return true;
    }
}

