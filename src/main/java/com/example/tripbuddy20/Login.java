package com.example.tripbuddy20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripbuddy20.databinding.ActivityLoginBinding;


public class Login extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button btnlogin;
    EditText email, password;
    ActivityLoginBinding binding;
    LoginDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);

                    binding = ActivityLoginBinding.inflate(getLayoutInflater());
                    setContentView(binding.getRoot());
                    database = new LoginDB(this);
                    binding.btnlogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String email = binding.email.getText().toString();
                            String password = binding.password.getText().toString();


                            if(email.equals("")||password.equals(""))
                                Toast.makeText(Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                            else{
                                Boolean checkCredentials = database.checkEmailPassword(email, password);
                                if(checkCredentials ){
                                    SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                                    boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        startActivity(new Intent(Login.this, Home.class));
                                        finish();
                                    }


                                        Toast.makeText(Login.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent  = new Intent(getApplicationContext(), Home.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Login.this, Registration.class);
                            startActivity(intent);
                        }
                    });



    }
}