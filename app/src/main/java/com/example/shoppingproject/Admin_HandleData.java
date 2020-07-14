package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_HandleData extends AppCompatActivity {

    Button handleCategory;
    Button handleProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__handle_data);

        handleCategory = findViewById(R.id.Handlecategory);
        handleProducts = findViewById(R.id.HandleProducts);

        handleCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_HandleData.this, AdminEditCategory.class);
                startActivity(intent);
            }
        });
        handleProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_HandleData.this, AdminHandleProducts.class);
                startActivity(intent);
            }
        });
    }
}
