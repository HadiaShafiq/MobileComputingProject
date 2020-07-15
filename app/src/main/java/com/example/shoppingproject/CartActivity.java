package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    Toolbar myToolbar;
    DBHelper db;
    TextView empty;
    List<CartProducts> cartProductData = new ArrayList<CartProducts>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.my_menu);

        String uname = getIntent().getStringExtra("userName");
        db = new DBHelper(this);
        int uid =db.getUserId(uname);
        cartProductData = db.getCart(uid);
        empty= findViewById(R.id.Message);
        if (cartProductData.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
        }
        else{
            empty.setVisibility(View.GONE);
            RecyclerView recyclerViewProduct = (RecyclerView) findViewById(R.id.Cartproductlist);
            LinearLayoutManager linearLayoutManagerProduct = new LinearLayoutManager(getApplicationContext());
            //linearLayoutManagerProduct.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
            recyclerViewProduct.setLayoutManager(linearLayoutManagerProduct); // set LayoutManager to RecyclerView
            CartAdapter customCartAdapter = new CartAdapter(CartActivity.this, cartProductData);
            recyclerViewProduct.setAdapter(customCartAdapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }
}
