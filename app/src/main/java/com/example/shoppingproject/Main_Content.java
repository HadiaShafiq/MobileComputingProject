package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;


public class Main_Content extends AppCompatActivity {
    //on logou button do Paper.book().destroy();
    Toolbar myToolbar;
    DBHelper db;
    Button handleData;
    CategoryAdapter adapter;
    List<Category> data = new ArrayList<Category>();
    List<Product> productData = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__content);
        myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.my_menu);
        //myToolbar.setLogo(R.drawable.app_logo);

        db = new DBHelper(this);
        data = db.getCategories();
        productData = db.getProduct();

        RecyclerView recyclerViewCategory = (RecyclerView) findViewById(R.id.categorylist);
        LinearLayoutManager linearLayoutManagerCategory = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerCategory.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        recyclerViewCategory.setLayoutManager(linearLayoutManagerCategory); // set LayoutManager to RecyclerView
        CategoryAdapter customAdapter = new CategoryAdapter(Main_Content.this, data);
        recyclerViewCategory.setAdapter(customAdapter);

        RecyclerView recyclerViewProduct = (RecyclerView) findViewById(R.id.productlist);
        LinearLayoutManager linearLayoutManagerProduct = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerProduct.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        recyclerViewProduct.setLayoutManager(linearLayoutManagerProduct); // set LayoutManager to RecyclerView
        ProductAdapter customProductAdapter = new ProductAdapter(Main_Content.this, productData);
        recyclerViewProduct.setAdapter(customProductAdapter);


        Paper.init(this);
        String name = Paper.book().read(Users.userName);
        handleData = findViewById(R.id.handle);
        if (name.equals("user")) {
            handleData.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        } else {
            handleData.setVisibility(View.GONE);
        }
        handleData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Main_Content.this, Admin_HandleData.class);
                startActivity(registerIntent);
            }
        });
        Toast.makeText(getApplicationContext(), "in" + name + "else", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    //private void setUpImage(byte[] bytes) {
      //  Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //imageView.setImageBitmap(bitmap);
    //}
}

