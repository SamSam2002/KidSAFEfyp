package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    TextView txtView;
    EditText editTextEmail, editTextPassword;
    Button login;
    password_db myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        myDb= new password_db(this);
        editTextEmail= findViewById(R.id.email1);
        editTextPassword = findViewById(R.id.password1);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempt_login();
            }
        });
        final TextView txtView = this.findViewById(R.id.txtView);
        Intent intent;
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void attempt_login() {
        String email =editTextEmail.getText().toString();
        String password= editTextPassword.getText().toString();
        if (email.equals("")||password.equals(""))
            Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        else{
            Boolean checkuserpass = myDb.checkusernamepassword(email, password);
            if(checkuserpass==true){
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Message")
                        .setContentText("Signed in Successfully")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(LoginActivity.this, ParentHomeActivity2.class);
                                startActivity(i);
                            }
                        })
                        .show();

            }else{
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }




    }



}