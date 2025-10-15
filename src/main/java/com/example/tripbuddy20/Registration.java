package com.example.tripbuddy20;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripbuddy20.databinding.ActivityRegistrationBinding;

public class Registration extends AppCompatActivity {

        ActivityRegistrationBinding binding;
        LoginDB database;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            // Use ViewBinding properly
            binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            database = new LoginDB(this);

            binding.btnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = binding.edtEmail.getText().toString().trim();
                    String password = binding.edtpassword.getText().toString().trim();
                    String confirmPassword = binding.edtconfirm.getText().toString().trim();

                    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(Registration.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.equals(confirmPassword)) {
                            boolean checkUserEmail = database.checkEmail(email);
                            if (!checkUserEmail) {
                                boolean insert = database.insertData( email, password);
                                if (insert) {

                                    if (database.checkEmailPassword(email, password)) {
                                        // Save user email in SharedPreferences
                                        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                                        prefs.edit().putString("email", email).apply();
                                    }
                                        Toast.makeText(Registration.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registration.this, Home.class);
                                    startActivity(intent);


                                } else {
                                    Toast.makeText(Registration.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Registration.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Registration.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            binding.loginRedirect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                }
            });

        }

}