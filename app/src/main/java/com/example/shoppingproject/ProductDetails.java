package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProductDetails extends AppCompatActivity {

    Toolbar myToolbar;
    DBHelper db;
    Product productData = new Product();
    ImageView productImgIV;
    TextView productNameTV;
    TextView proPrice;
    TextView qty;
    Button addQty, subQty;
    Button addtocart, order;
    private int counter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.my_menu);

        final String name = getIntent().getStringExtra("ProductName");
        db = new DBHelper(this);
        productData =db.getProduct(name);

        productImgIV =(ImageView)findViewById(R.id.productImg);
        //convert byte to bitmap take from Category class
        byte[] outImage=productData.getproductImg();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        productImgIV.setImageBitmap(theImage);

        productNameTV =(TextView)findViewById(R.id.productName);
        productNameTV.setText(name);

        proPrice =(TextView)findViewById(R.id.proPrice);
        proPrice.setText(Integer.toString(productData.getproductprice()));

        addQty = (Button)findViewById(R.id.addQty);
        subQty = (Button)findViewById(R.id.subQty);
        qty = (TextView)findViewById(R.id.qty);
        String quantity=qty.getText().toString();
        addQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productData.getproductinStock()<counter) {
                    int left = counter - productData.getproductinStock();
                    Toast.makeText(getApplicationContext(), "Less in stock you can order up to " + left , Toast.LENGTH_LONG).show();
                }
                else {
                    counter++;
                    qty.setText(Integer.toString(counter));
                }
            }
        });
        subQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter!=1){
                    counter--;
                    qty.setText(Integer.toString(counter));
                }
            }
        });
        Paper.init(this);
        final String uname =Paper.book().read(Users.userName);
        final int uid = db.getUserId(uname);
        addtocart = (Button) findViewById(R.id.addToCart);
        order = (Button)findViewById(R.id.order);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price =productData.getproductprice()*counter;
                db.addToCart(name,uid,counter,price);
                Toast.makeText(getApplicationContext(), "added to cart", Toast.LENGTH_LONG).show();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "your order is on the way", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

}
