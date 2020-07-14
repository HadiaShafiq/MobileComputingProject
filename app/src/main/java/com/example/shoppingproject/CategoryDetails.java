package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetails extends AppCompatActivity {

    Toolbar myToolbar;
    TextView categoryNametv;
    DBHelper db;
    List<Product> productData = new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.my_menu);

        String name = getIntent().getStringExtra("categoryName");
        db = new DBHelper(this);
        productData = db.getCategoryRelatedProduct(name);
        if (productData.isEmpty()) {
            Toast.makeText(getApplicationContext(), "COMING SOON", Toast.LENGTH_SHORT).show();
            Intent registerIntent = new Intent(CategoryDetails.this, Main_Content.class);
            startActivity(registerIntent);
        }
        else {
            RecyclerView recyclerViewProduct = (RecyclerView) findViewById(R.id.productlist);
            LinearLayoutManager linearLayoutManagerProduct = new LinearLayoutManager(getApplicationContext());
            //linearLayoutManagerProduct.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
            recyclerViewProduct.setLayoutManager(linearLayoutManagerProduct); // set LayoutManager to RecyclerView
            ProductAdapter customProductAdapter = new ProductAdapter(CategoryDetails.this, productData);
            recyclerViewProduct.setAdapter(customProductAdapter);

            categoryNametv = (TextView) findViewById(R.id.categoryName);
            categoryNametv.setText(name);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}