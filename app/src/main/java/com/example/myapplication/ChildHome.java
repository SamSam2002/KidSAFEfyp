package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class ChildHome extends AppCompatActivity {
    GridLayout mainGrid;
    TextView scr;
    TextView blck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);
        scr= findViewById(R.id.scr);
        blck = findViewById(R.id.blck);
        scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ChildHome.this,ScreenTimeactivity.class);
                startActivity(intent);
                finish();
            }
        });





    }
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ChildHome.this, ChildHome.class));
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent =new Intent(ChildHome.this,ChildLogin.class);
        startActivity(intent);
        finish();

        return true;
    }
}