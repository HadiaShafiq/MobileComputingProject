package com.example.shoppingproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_form extends AppCompatActivity {
    DBHelper db;
    EditText userName;
    EditText password;
    Button login;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=new DBHelper(this);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
//        getActionBar().setTitle(" Login To buy ");
        userName=(EditText)findViewById(R.id.UserName);
        password=(EditText)findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.Register);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent registerIntent= new Intent(Login_form.this,Register_Activity.class)
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
                    if(validate==true)
                        Toast.makeText(getApplicationContext(),"yaaaaa",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
                    //Boolean check= db.CheckUserName(name);
                    //if(check==true){
                        //Boolean insert =db.insert(name,pswd);
                        //if(insert==true)
                          //  Toast.makeText(getApplicationContext(),"yaaaaa",Toast.LENGTH_SHORT).show();
                        //else
                            //Toast.makeText(getApplicationContext(),"yikes",Toast.LENGTH_SHORT).show();
                    //}
                    //else
                       // Toast.makeText(getApplicationContext(),"nooooooooo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}