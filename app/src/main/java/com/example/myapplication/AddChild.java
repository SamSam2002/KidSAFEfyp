package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddChild extends AppCompatActivity {
    EditText name, pin;
    Button add;
    Child_PINdb myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        myDatabase = new Child_PINdb(this);
        name= findViewById(R.id.name_edittext);
        pin = findViewById(R.id.pin_edittext);
        Register_User();


    }

    private void Register_User() {
        add =findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String PIN = pin.getText().toString();
                if (Name.equals(" ")||PIN.equals(""))
                    Toast.makeText(AddChild.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else {
                    if (PIN.equals(PIN)) {
                        Boolean checkname = myDatabase.checkname(Name);
                        if (checkname == false) {
                            Boolean insert = myDatabase.insertData(Name, PIN);
                            if (insert == true) {
                                new SweetAlertDialog(AddChild.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Message")
                                        .setContentText("Your child has been added")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent i = new Intent(AddChild.this, ParentHome.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            } else {
                                Toast.makeText(AddChild.this, "Registration failed", Toast.LENGTH_SHORT).show();


                            }
                        } else {
                            Toast.makeText(AddChild.this, "Child already exists", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(AddChild.this, "Passwords not matching", Toast.LENGTH_SHORT).show();

                    }

                }







            }
        });






    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(AddChild.this, ParentHome.class));
        finish();

    }
}






