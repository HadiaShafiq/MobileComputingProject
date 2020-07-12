package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AdminHandleProducts extends AppCompatActivity {

    Button addProduct;
    Button delProduct;
    Button editProduct;
    Button  pickImg;
    Button total;
    EditText ProductName;
    EditText ProductQty;
    EditText ProductPrice;
    EditText ProductOnSale;
    EditText ProductDiscount;
    EditText ProductDesc;
    EditText CategoryName;
    EditText delProductName;
    DBHelper db;
    ImageView ProductImg;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_handle_products);

        ProductName=(EditText)findViewById(R.id.ProductName);
        delProductName=(EditText)findViewById(R.id.deleteProductName);
        ProductQty=(EditText)findViewById(R.id.quantity);
        ProductPrice=(EditText)findViewById(R.id.price);
        ProductOnSale=(EditText)findViewById(R.id.onSale);
        ProductDiscount=(EditText)findViewById(R.id.discount);
        ProductDesc=(EditText)findViewById(R.id.desc);
        CategoryName=(EditText)findViewById(R.id.CategoryName);


        addProduct = findViewById(R.id.addBtnProduct);
        ProductImg = findViewById(R.id.ProductImg);
        pickImg =findViewById(R.id.pickProductImg);
        total = findViewById(R.id.TotalProduct);
        delProduct = findViewById(R.id.delProduct);

        delProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name= delProductName.getText().toString();
                int c= db.deleteProduct(name);
                Toast.makeText(getApplicationContext(),c +"in delete",Toast.LENGTH_LONG).show();
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = getProductCount();
                Toast.makeText(getApplicationContext(),c+"count",Toast.LENGTH_LONG).show();
            }
        });
        pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name= ProductName.getText().toString();
                String q= ProductQty.getText().toString();
                int  qty =0;
                if(q.equals(""))
                    Toast.makeText(getApplicationContext(),"enter a Product quantity ",Toast.LENGTH_SHORT).show();
                else
                    qty = Integer.parseInt(q);
                String p=ProductPrice.getText().toString();
                int  price =0;
                if(p.equals(""))
                    Toast.makeText(getApplicationContext(),"enter a Product price ",Toast.LENGTH_SHORT).show();
                else
                    price = Integer.parseInt(p);
                String  desc= ProductDesc.getText().toString();
                if(desc.equals("")){
                    Toast.makeText(getApplicationContext(),"enter a Product description ",Toast.LENGTH_SHORT).show();
                }
                int  onSale=0;
                String os =ProductOnSale.getText().toString();
                if(os.equals(""))
                    Toast.makeText(getApplicationContext(),"enter a Product price ",Toast.LENGTH_SHORT).show();
                else
                    onSale = Integer.parseInt(os);
                String od = ProductDiscount.getText().toString();
                int  ondiscount = 0;
                if(od.equals(""))
                    Toast.makeText(getApplicationContext(),"enter a Product discount ",Toast.LENGTH_SHORT).show();
                else
                    ondiscount = Integer.parseInt(od);
                String  categoryName= CategoryName.getText().toString();
                if(categoryName.equals("")){
                    Toast.makeText(getApplicationContext(),"enter a Product category name ",Toast.LENGTH_SHORT).show();
                }
                if(name.equals("")){
                    Toast.makeText(getApplicationContext(),"enter a Product name ",Toast.LENGTH_SHORT).show();
                }
                if(ProductImg.getDrawable() == null ){
                    Toast.makeText(getApplicationContext(),"pick a Product image ",Toast.LENGTH_SHORT).show();
                }
                else {
                    String msg = insertProduct(name,price,qty,ondiscount,onSale,categoryName,desc);
                    Toast.makeText(getApplicationContext(),msg ,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            ProductImg.setImageURI(imageUri);
        }
    }
    public String insertProduct(String name, int price, int quantity, int discount, int sale ,String categoryName, String desc){
        db=new DBHelper(this);
        // get image from drawable
        // Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.clothes);
        Bitmap image =((BitmapDrawable) ProductImg.getDrawable()).getBitmap();
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        String t =db.insertProduct(name ,imageInByte, price, quantity, sale, discount, categoryName , desc);
        return t;
    }
    public int getProductCount(){
        db=new DBHelper(this);
        int co = db.getProductCount();
        //Toast.makeText(getApplicationContext(),co ,Toast.LENGTH_SHORT).show();

        return co;
    }
}
