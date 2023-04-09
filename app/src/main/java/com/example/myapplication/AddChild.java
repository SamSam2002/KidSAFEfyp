package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.API.RetrofitClient;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChild extends AppCompatActivity {

    EditText editTextname, editTextemail, editTextpassword;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        editTextname = findViewById(R.id.name_edittext);
        editTextemail = findViewById(R.id.email_edittext);
        editTextpassword= findViewById(R.id.password_edittext);
        Register_Child();
        register = findViewById(R.id.add_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register_Child();
            }
        });
    }

    private void Register_Child() {
        String name = editTextname.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        String role = "child"; // set default role as child
        if (name.isEmpty()||(email.isEmpty() || password.isEmpty() || role.isEmpty())) {
            Toast.makeText(AddChild.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
//        Call<ChildResponse> call = RetrofitClient
//                .getInstance()
//                .getAPI()
//                .addChild(name,email, pin);
//      call.enqueue(new Callback<ChildResponse>() {
//          @Override
//          public void onResponse(Call<ChildResponse> call, Response<ChildResponse> response) {
//              if (response.isSuccessful()) {
//                  ChildResponse registerrResponse = response.body();
//                  if (registerrResponse != null) {
//
//                      new SweetAlertDialog(AddChild.this, SweetAlertDialog.SUCCESS_TYPE)
//                              .setTitleText("Message")
//                              .setContentText("Your child has been added")
//                              .setConfirmText("Ok")
//                              .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                  @Override
//                                  public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                      Intent i = new Intent(AddChild.this, ParentHome.class);
//                                      startActivity(i);
//                                  }
//                              })
//                              .show();
//                  }
//              } else {
//                  try {
//                      String errorBody = response.errorBody().string();
//                      Log.e("REGISTER_ERROR", errorBody);
//                      Toast.makeText(AddChild.this, errorBody , Toast.LENGTH_SHORT).show();
//                  } catch (IOException e) {
//                      e.printStackTrace();
//                  }
//              }
//          }
//
//
//
//          @Override
//          public void onFailure(Call<ChildResponse> call, Throwable t) {
//              Log.e("REGISTER_FAILURE", t.getMessage());
//              Toast.makeText(AddChild.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//          }
//      });
//
//
//
//    }
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

                        new SweetAlertDialog(AddChild.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Message")
                                .setContentText("Registration of Your Child is succesful!")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(AddChild.this, ParentHome.class);
                                        startActivity(i);
                                    }
                                })
                                .show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("REGISTER_ERROR", errorBody);
                        Toast.makeText(AddChild.this, errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("REGISTER_FAILURE", t.getMessage());
                Toast.makeText(AddChild.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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






