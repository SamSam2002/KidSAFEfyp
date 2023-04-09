package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.API;
import com.example.myapplication.API.RetrofitClient;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView txtView;
    EditText editTextEmail, editTextPassword;
    Button login;
    Button child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

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
        child= findViewById(R.id.child);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this, ChildLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void attempt_login() {
      String email =editTextEmail.getText().toString().trim();
      String password = editTextPassword.getText().toString().trim();
      String role = "parent";
      Call<LoginResponse> call = RetrofitClient
              .getInstance()
              .getAPI()
              .login(email, password, role);
      call.enqueue(new Callback<LoginResponse>() {
          @Override
          public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
             LoginResponse loginResult = response.body();

              if(response.body() != null) {
                  if (role == "parent") {
                      if (!loginResult.isError()) {
                          Intent intent = new Intent(LoginActivity.this, ParentHome.class);
                          startActivity(intent);
                          finish();

                      } else {
                          Toast.makeText(LoginActivity.this, loginResult.getMessage(), Toast.LENGTH_LONG).show();

                      }
                  }
              }
          }

          @Override
          public void onFailure(Call<LoginResponse> call, Throwable t) {
              Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

          }
      });
            }


        }








