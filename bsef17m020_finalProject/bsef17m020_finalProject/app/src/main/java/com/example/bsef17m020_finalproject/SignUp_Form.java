package com.example.bsef17m020_finalproject;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignUp_Form extends AppCompatActivity {

    DatabaseHelper db;
    private static final String TAG = "RegisterActivity";
    private static final String URL_FOR_REGISTRATION = "https://XXX.XXX.X.XX/android_login_example/register.php";
    ProgressDialog progressDialog;
    private EditText signupInputName, signupInputPassword, signupInputCnfPassword;
    private Button btnSignUp;
    private TextView btnLinkLogin;
    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);
        db = new DatabaseHelper(this);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        signupInputName = (EditText) findViewById(R.id.edittext_username);
        signupInputPassword = (EditText) findViewById(R.id.edittext_password);
        signupInputCnfPassword = (EditText) findViewById(R.id.edittext_cnf_password);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkLogin = (TextView) findViewById(R.id.textview_login);

        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(SignUp_Form.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                String gender;
                if (selectedId == R.id.female_radio_btn)
                    gender = "Female";
                else
                    gender = "Male";
                String user = signupInputName.getText().toString().trim();
                String pwd = signupInputPassword.getText().toString().trim();
                String cnf_pwd = signupInputCnfPassword.getText().toString().trim();
                if (pwd.equals(cnf_pwd)) {
                    long val = db.addUser(user, pwd);
                    if (val > 0) {
                        Toast.makeText(SignUp_Form.this, "You have registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(SignUp_Form.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    } else {
                        Toast.makeText(SignUp_Form.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SignUp_Form.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}