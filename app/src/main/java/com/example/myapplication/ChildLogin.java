package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.RetrofitClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildLogin extends AppCompatActivity {
    TextView txtView;
    EditText editTextemail, editTextpassword;
    Button login;

    Button backparent_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_login);

        editTextemail= findViewById(R.id.email_editext);
        editTextpassword = findViewById(R.id.pass_editext);
        login=findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Child_login();
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

    private void Child_login() {
        String email =editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String role = "child";
        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getAPI()
                .login(email, password,role);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResult = response.body();

                if(response.body() != null) {
                    if (role == "child") {
                        if (!loginResult.isError()) {
                            Intent intent = new Intent(ChildLogin.this, ChildHome.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(ChildLogin.this, loginResult.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ChildLogin.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }



}