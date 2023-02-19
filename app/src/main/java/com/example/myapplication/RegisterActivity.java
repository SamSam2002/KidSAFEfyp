package com.example.myapplication;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextRepass;
    Button register;
    Button back_to_login;
    password_db myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDatabase = new password_db(this);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextRepass = findViewById(R.id.Repass);


        Register_User();
        back_to_login = findViewById(R.id.back_to_login);
        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }

        });


    }

    private void Register_User() {
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String repass= editTextRepass.getText().toString();
                if (email.equals(" ")||password.equals("")||repass.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(repass)) {
                        Boolean checkuser = myDatabase.checkusername(email);
                        if (checkuser == false) {
                            Boolean insert = myDatabase.insertData(email, password);
                            if (insert == true) {
                                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Message")
                                        .setContentText("You are Registered")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();


                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();

                    }
                }







            }
        });






    }
}