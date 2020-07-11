package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import io.paperdb.Paper;

public class Login_form extends AppCompatActivity {
    DBHelper db;
    EditText userName;
    EditText password;
    Button login;
    Button signup;
    CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=new DBHelper(this);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        //getActionBar().setTitle(" Login To buy ");
        userName=(EditText)findViewById(R.id.UserName);
        password=(EditText)findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.Register);

        remember=findViewById(R.id.rememberMeBox);
        Paper.init(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent= new Intent(Login_form.this,Signup_form.class);
                startActivity(registerIntent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= userName.getText().toString();
                String pswd=password.getText().toString();
                if(name.equals("")||pswd.equals(""))
                    Toast.makeText(getApplicationContext(),"Fields are Empty",Toast.LENGTH_SHORT).show();
                else{
                    Boolean validate=db.validateUser(name,pswd);
                    if(validate==true) {
                        db.getUserId(name);
                        int id = db.getUserId(name);
                        allowAccess(name,pswd, id);
                        startActivity(new Intent(getApplicationContext(),Main_Content.class));
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
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
