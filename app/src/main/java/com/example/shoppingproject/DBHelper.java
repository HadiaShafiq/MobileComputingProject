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
        db.execSQL("Create table Product(ProductID integer primary key autoincrement, ProductName text, ProductImage BLOB, price integer, quantity integer, sold integer, inStock integer, onOrder integer, description text, onSale integer DEFAULT 0, discount integer, CategoryID integer ,FOREIGN KEY(CategoryID)  references Category(CategoryID) On delete cascade )");
        db.execSQL("Create table Cart(CartID integer primary key autoincrement,  TotalPrice integer, ProductID integer , UserID integer, FOREIGN KEY(UserID)  references users  On delete cascade , FOREIGN KEY(ProductID) references Product(ID) On delete cascade )");
        db.execSQL("Create table Orders(OrderID integer primary key autoincrement, TotalPrice integer,  quantity integer, UserID integer, ProductID integer ,FOREIGN KEY(UserID)  references users(ID) On delete cascade,  FOREIGN KEY(ProductID)  references users(ProductID) On delete cascade )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users" );
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    //for user table
    public int getUserId(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select ID from users",null);
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
    public boolean CheckUserName(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select UserName from users where UserName = ?", new String[]{name});
        int c = cursor.getCount();
        cursor.close();
        if(c>0)
            return false;
        return true;
    }

    // for category table
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
        Cursor cursor = db.rawQuery("Select * from Category ",null);//where UserName = ?", new String[]{name});
        int a= 0;
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
    public int getCategoryId(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select CategoryID from Category where CategoryName = ?", new String[]{name});
        int id =cursor.getInt(0);
        cursor.close();
        return id;
    }

    // for product table
    public String insertProduct(String ProductName, byte[] imageBytes, int price, int quantity, int discount, int sale ,String desc , String categoryName ){
        SQLiteDatabase db =this.getWritableDatabase();
        int id = getCategoryId(categoryName);
        if(id==-1) {
            return "this category does not exist";
        }
        else {
            if (CheckProductName(ProductName) == true) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("ProductName", ProductName);
                contentValues.put("ProductImage", imageBytes);
                contentValues.put("price", price);
                contentValues.put("quantity", quantity);
                contentValues.put("sold", 0);
                contentValues.put("inStock", quantity);
                contentValues.put("onOrder", 0);
                contentValues.put("description", desc);
                contentValues.put("onSale", sale);
                contentValues.put("discount", discount);
                contentValues.put("CategoryID", id);
                long ins = db.insert("Product", null, contentValues);
                if (ins == -1)
                    return "inserted";
                return "error has occurred";
            } else
                return "this Product already exists";
        }
    }
    public List<Product> getProduct(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Product ",null);
        List<Product> productList = new ArrayList<Product>();
        Product product ;
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                product =new Product(); //by adding this last item was not dispalyed the total number of item times
                product.setproductName(cursor.getString(cursor.getColumnIndex("ProductName")));
                product.setproductImg(cursor.getBlob(cursor.getColumnIndex("ProductImage")));
                product.setproductId(cursor.getInt(0));
                product.setproductdescription(cursor.getString(cursor.getColumnIndex("description")));
                product.setproductcategoryName(cursor.getString(cursor.getColumnIndex("description")));
                product.setproductdiscount(cursor.getInt(cursor.getColumnIndex("discount")));
                product.setproductsale(cursor.getInt(cursor.getColumnIndex("onSale")));
                product.setproductquantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                product.setproductprice(cursor.getInt(cursor.getColumnIndex("price")));
                product.setproductsold(cursor.getInt(cursor.getColumnIndex("sold")));
                product.setproductinStock(cursor.getInt(cursor.getColumnIndex("inStock")));
                cursor.moveToNext();
                productList.add(product);
            }
        }
        cursor.close();
        return productList;
    }
    public boolean CheckProductName(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select ProductName from Product where ProductName = ?", new String[]{name});
        int c =cursor.getCount();
        cursor.close();
        if(c >0)
            return false;
        return true;
    }
    public int getProductCount(){
        SQLiteDatabase db=this.getReadableDatabase();
        String name="user";
        Cursor cursor = db.rawQuery("Select * from Product ",null);//where UserName = ?", new String[]{name});
        int a= 0;
        if(cursor.moveToFirst()) {
            // c =cursor.getString(cursor.getColumnIndex("Password"));
            a=cursor.getCount();
        }
        return a;
    }
    public int deleteProduct(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        String whereClause ="ProductName = ?";
        String[] arguments ={name};
        return db.delete("Product", "ProductName" + "='" + name +"' ;", null) ;
    }

}