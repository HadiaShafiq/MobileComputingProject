package com.example.shoppingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class Signup_form extends AppCompatActivity {
    DBHelper db;
    EditText userName;
    EditText password;
    Button login;
    Button signup;
    CheckBox remember;
    //Email Validation pattern
    //public static final String regEx = "\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=new DBHelper(this);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);
        //getActionBar().setTitle(" Login To buy ");
        userName=(EditText)findViewById(R.id.UserName);
        password=(EditText)findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.Register);

        remember=findViewById(R.id.rememberMeBox);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Signup_form.this,Login_form.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= userName.getText().toString();
                String pswd=password.getText().toString();
                if(name.equals("")||pswd.equals(""))
                    Toast.makeText(getApplicationContext(),"Fields are Empty",Toast.LENGTH_SHORT).show();
                else {
                    String insert = db.insert(name, pswd);
                    if (insert == "user inserted") {
                        Toast.makeText(getApplicationContext(), "Let's Shop", Toast.LENGTH_SHORT).show();
                        int id = db.getUserId(name);
                        allowAccess(name, pswd, id);
                        startActivity(new Intent(getApplicationContext(), Main_Content.class));
                    } else if (insert == "chose another username")
                        Toast.makeText(getApplicationContext(), "chose another username", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Some error has occurred. Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void allowAccess(String name, String password, int id){
        if(remember.isChecked()){
            Paper.book().write(Users.id,id);
            Paper.book().write(Users.userName,name);
            Paper.book().write(Users.password,password);
        }
    }
}
