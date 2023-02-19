package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChildLogin extends AppCompatActivity {
    TextView txtView;
    EditText name, pin;
    Button login;
    Child_PINdb myDb;
    Button backparent_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_login);
        myDb= new Child_PINdb(this);
        name= findViewById(R.id.name_editext);
        pin= findViewById(R.id.pin_editext);
        login=findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempt_login();
            }
        });
        backparent_button=findViewById(R.id.backparent_button);
        backparent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ChildLogin.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void attempt_login() {
        String Name =name.getText().toString();
        String PIN= pin.getText().toString();
        if (Name.equals("")||PIN.equals(""))
            Toast.makeText(ChildLogin.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        else{
            Boolean checknamepin = myDb.checknamepin(Name, PIN);
            if(checknamepin==true){
                new SweetAlertDialog(ChildLogin.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Message")
                        .setContentText("WELCOME TO CHILD'S DEVICE")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(ChildLogin.this, ChildHome.class);
                                startActivity(i);
                            }
                        })
                        .show();

            }else{
                Toast.makeText(ChildLogin.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }
}