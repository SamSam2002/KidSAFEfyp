package com.example.myapplication;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.API.API;
import com.example.myapplication.API.RetrofitClient;

import java.io.IOException;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity  {

    EditText editTextEmail, editTextPassword, editTextname, editTextrole;
    Button register;
    Button back_to_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextname= findViewById(R.id.name);


        Register_User();
        back_to_login = findViewById(R.id.back_to_login);
        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }

        });

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register_User();
            }
        });


    }

    private void Register_User() {
        String name = editTextname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String role = "parent"; // set default role as parent


        if (name.isEmpty()||(email.isEmpty() || password.isEmpty() || role.isEmpty())) {
            Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            return;
        }


        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getAPI()
                .registration(name,email, password, role);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerrResponse = response.body();
                    if (registerrResponse != null) {
                        if (role == "parent") {

                            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Message")
                                    .setContentText("Registration succesful!")
                                    .setConfirmText("Ok")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(i);
                                        }
                                    })
                                    .show();
                        }
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("REGISTER_ERROR", errorBody);
                        Toast.makeText(RegisterActivity.this, errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("REGISTER_FAILURE", t.getMessage());
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }




