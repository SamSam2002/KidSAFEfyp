package com.example.myapplication.API;

import com.example.myapplication.ChildLoginResponse;
import com.example.myapplication.ChildResponse;
import com.example.myapplication.LoginResponse;
import com.example.myapplication.RegisterResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

   @FormUrlEncoded
    @POST("registration")
    Call<RegisterResponse> registration(
            @Field("name")String name,
            @Field("email") String email,
            @Field("password") String password,
              @Field("role") String role

   );
   @FormUrlEncoded
    @POST("login")
    Call<LoginResponse>login(
           @Field("email") String email,
           @Field("password") String password,
           @Field("role") String role
   );


}