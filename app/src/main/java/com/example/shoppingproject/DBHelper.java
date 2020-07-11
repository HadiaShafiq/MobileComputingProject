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
        cursor.close();
        return id;
    }

    public String insert(String name, String password){
        SQLiteDatabase db =this.getWritableDatabase();
        if(CheckUserName(name)==true) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("UserName", name);
            contentValues.put("Password", password);
            long ins = db.insert("users", null, contentValues);
            if (ins == -1)
                return "user inserted";
            return "some error has occurred";
        }else
            return "chose another username";
    }
    public boolean validateUser(String name, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select UserName, Password from users where UserName = ? and Password = ?", new String[]{name, password});
        int c = cursor.getCount();
        cursor.close();
        if(c>0)
            return true;
        return false;
    }

    public String insertCategory(String CategoryName, byte[] imageBytes){
        SQLiteDatabase db =this.getWritableDatabase();
        if(CheckCategoryName(CategoryName)==true) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("CategoryName", CategoryName);
            contentValues.put("CategoryImage", imageBytes);
            long ins = db.insert("Category", null, contentValues);
            if (ins == -1)
                return "inserted";
            return "error has occurred";
        }else
            return "this category already exists";
    }
    public List<Category> getCategories(){
        SQLiteDatabase db=this.getReadableDatabase();
        String name ="Clothes";
        Cursor cursor = db.rawQuery("Select * from Category ",null);//where CategoryName = ?",new String[]{name});
        List<Category> categoryList = new ArrayList<Category>();
        Category category ;
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                category =new Category(); //by adding this last item was not dispalyed the total number of item times
                category.setcategoryName(cursor.getString(cursor.getColumnIndex("CategoryName")));
                category.setcategoryImg(cursor.getBlob(cursor.getColumnIndex("CategoryImage")));
                category.setCategoryId(cursor.getInt(0));
                cursor.moveToNext();
                categoryList.add(category);
            }
            //do{
              //  category.setCategoryId(cursor.getInt(0));
                //category.setcategoryName(cursor.getString(1));
               // category.setcategoryImg(cursor.getBlob(2));
               // categoryList.add(category);
            //}while (cursor.moveToNext());
        }
        cursor.close();
        return categoryList;
    }

    public boolean CheckUserName(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select UserName from users where UserName = ?", new String[]{name});
        int c = cursor.getCount();
        cursor.close();
        if(c>0)
            return false;
        return true;
    }

    public boolean CheckCategoryName(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select CategoryName from Category where CategoryName = ?", new String[]{name});
        int c =cursor.getCount();
        cursor.close();
        if(c >0)
            return false;
        return true;
    }
    public int getCategoryCount(){
        SQLiteDatabase db=this.getReadableDatabase();
        String name="user";
        Cursor cursor = db.rawQuery("Select * from users ",null);//where UserName = ?", new String[]{name});
        int a= 1982;
        if(cursor.moveToFirst()) {
           // c =cursor.getString(cursor.getColumnIndex("Password"));
            a=cursor.getCount();
        }
        return a;
    }

    public int deleteCategory(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        String whereClause ="CategoryName = ?";
        String[] arguments ={name};
        return db.delete("Category", "CategoryName" + "='" + name +"' ;", null) ;
    }
}