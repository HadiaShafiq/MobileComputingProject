package com.example.shoppingproject;

public class CartProducts {
    int productID;
    int userId;
    int qty;
    int price;
    private byte[] productImg;
    private String productName;

    public  CartProducts(){

    }

    public  CartProducts(int productID, int qty, int price){
        this.price=price;
        this.productID=productID;
        this.qty=qty;
    }

    public void setproductName(String  productName){
        this.productName = productName;
    }
    public String getproductName(){
        return this.productName;
    }

    public void setproductImg(byte[] img){
        this.productImg = img;
    }
    public byte[] getproductImg(){
        return this.productImg;
    }

    public void setproductID(int productID){
        this.productID = productID;
    }
    public int getproductID(){
        return this.productID;
    }

    public void setuserId(int userId){
        this.userId = userId;
    }
    public int getuserId(){
        return this.userId;
    }

    public void setPrice(int price){
        this.price = price;
    }
    public int getPrice(){
        return this.price;
    }

    public void setqty(int qty){
        this.qty = qty;
    }
    public int getqty(){
        return this.qty;
    }
}
