package com.example.shoppingproject;

import java.sql.Blob;

public class Category {
    private String categoryName;
    private int categoryId;
    private byte[] categoryImg;
    public  Category(){

    }
    public Category(int categoryId,String categoryName,  byte[] categoryImg) {
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
        this.categoryId = categoryId;
    }

    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }
    public int getCategoryId(){
        return this.categoryId;
    }

    public String getcategoryName() {
        return categoryName;
    }
    public void setcategoryName(String itemName) {
        this.categoryName = itemName;
    }

    public void setcategoryImg(byte[] img){
        this.categoryImg = img;
    }
    public byte[] getCategoryImg(){
        return this.categoryImg;
    }
}
