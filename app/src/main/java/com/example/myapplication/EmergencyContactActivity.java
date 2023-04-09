package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmergencyContactActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        nameEditText = findViewById(R.id.name_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the entered name and phone number
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                // Save the emergency contact information to shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("emergency_contact_name", name);
                editor.putString("emergency_contact_phone", phone);
                editor.apply();

                // Show a toast message to confirm that the information has been saved
                Toast.makeText(EmergencyContactActivity.this, "Emergency contact saved", Toast.LENGTH_SHORT).show();

                // Finish the activity and return to the main activity
                finish();
            }
        });
    }


}