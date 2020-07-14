package com.example.shoppingproject;

public class Product {
    private String productName;
    private int productId;
    private byte[] productImg;
    int price;
    int quantity;
    int sale;
    int discount;
    String description;
    String categoryName;
    int sold;
    int inStock;
    public  Product(){

    }
    public Product(int productId,String productName,  byte[] productImg, int price, int quantity ,int sale, int discount, String description, String categoryName) {
        this.productName = productName;
        this.productImg = productImg;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.sale = sale;
        this.discount = discount;
        this.description = description;
        this.categoryName = categoryName;
    }

    public void setproductId(int productId){
        this.productId = productId;
    }
    public int getproductId(){
        return this.productId;
    }

    public String getproductName() {
        return productName;
    }
    public void setproductName(String itemName) {
        this.productName = itemName;
    }

    public void setproductImg(byte[] img){
        this.productImg = img;
    }
    public byte[] getproductImg(){
        return this.productImg;
    }

    public void setproductprice(int productprice){
        this.price = productprice;
    }
    public int getproductprice(){
        return this.price;
    }

    public void setproductsale(int sale){
        this.sale = sale;
    }
    public int getproductsale(){
        return this.sale;
    }

    public void setproductquantity(int quantity){
        this.quantity = quantity;
    }
    public int getproductquantity(){
        return this.quantity;
    }

    public void setproductdiscount(int discount){
        this.discount = discount;
    }
    public int getproductdiscount(){
        return this.discount;
    }

    public String getproductcategoryName() {
        return categoryName;
    }
    public void setproductcategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getproductdescription() {
        return description;
    }
    public void setproductdescription(String description) {
        this.description = description;
    }

    public void setproductsold(int sold){
        this.sold = sold;
    }
    public int getproductsold(){
        return this.sold;
    }

    public void setproductinStock(int inStock){
        this.inStock = inStock;
    }
    public int getproductinStock(){
        return this.inStock;
    }
}
