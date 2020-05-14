package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Login_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle(" Login To buy ");
    }
}
