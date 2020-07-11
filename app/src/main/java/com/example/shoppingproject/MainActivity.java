package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    Button login;
    Button signup;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(MainActivity.this, Main_Content.class);
       // startActivity(intent);

        db = new DBHelper(this);

        login = findViewById(R.id.Home_loginBtn);
        signup = findViewById(R.id.Home_registerBtn);

        Paper.init(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this, Signup_form.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login_form.class);
                startActivity(intent);
            }
        });

        String name = Paper.book().read(Users.userName);
        String pswd = Paper.book().read(Users.password);
        String id =Paper.book().read(Users.id);
        if (name != "" && pswd != "") {
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pswd)) {
                allowAccess(name, pswd,id);
            }
        }
    }
    private void allowAccess(String name, String password, String id) {
        Boolean validate=false;
        if (name.equals("") || password.equals(""))
            Toast.makeText(getApplicationContext(), "Fields are Empty", Toast.LENGTH_SHORT).show();
        else {
            validate = db.validateUser(name, password);
        }
        if (validate == true) {
           // Toast.makeText(getApplicationContext(), "Lets' shop 1", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Main_Content.class));
        } else
            Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
    }
}

