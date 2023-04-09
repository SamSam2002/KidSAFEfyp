package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PinActivity extends AppCompatActivity {
    private EditText mPinEditText;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        Log.d("ChildHome", "Activity resumed");

        mSharedPreferences = getSharedPreferences("my_app_preferences", MODE_PRIVATE);
        mPinEditText = findViewById(R.id.pin_edit_text);

        // Check if there's already a saved PIN
        if (mSharedPreferences.contains("pin")) {
            // Show a message to remind the user of their saved PIN
            Toast.makeText(this, "Please enter your saved PIN", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.ok_button).setOnClickListener(v -> {
            String pin = mPinEditText.getText().toString();

            if (TextUtils.isEmpty(pin)) {
                Toast.makeText(this, "Please enter a PIN", Toast.LENGTH_SHORT).show();
            } else {
                // Store the entered PIN in SharedPreferences
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("pin", pin);
                editor.apply();

                Toast.makeText(this, "PIN saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
