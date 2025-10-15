package com.example.tripbuddy20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Japan extends AppCompatActivity {

    CheckBox sight, fine, fest, hiking;
    Button payment;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_japan);

        sight = findViewById(R.id.sight1);
        fine = findViewById(R.id.fine1);
        fest = findViewById(R.id.fest1);
        hiking = findViewById(R.id.hiking1);
        payment = findViewById(R.id.pay1);
        total = findViewById(R.id.total);

        sight.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
        fine.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
        fest.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
        hiking.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Japan.this, "Booking saved! Total: R" + total.getText(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Japan.this, Home.class);
                startActivity(intent);
            }
        });

    }

    private void updateTotal() {
        double totalPrice = 2500 + 2000; // Accommodation + Travel

        if (sight.isChecked()) {
            totalPrice += 150;
        }
        if (fest.isChecked()) {
            totalPrice += 300;
        }
        if (fine.isChecked()) {
            totalPrice += 250;
        }
        if(hiking.isChecked()){
            totalPrice += 250;
        }


        total.setText("Your total is: R"+ String.valueOf(totalPrice));
    }


}


