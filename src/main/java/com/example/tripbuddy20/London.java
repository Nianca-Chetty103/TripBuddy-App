package com.example.tripbuddy20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class London extends AppCompatActivity {

    CheckBox sight, fine, tour;
    Button payment;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_london);

        sight = findViewById(R.id.sight1);
        fine = findViewById(R.id.fine1);
        tour = findViewById(R.id.big);
        payment = findViewById(R.id.pay1);
        total = findViewById(R.id.total);

        sight.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
        fine.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());
        tour.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotal());


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(London.this, "Booking saved! Total: R" + total.getText(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(London.this, Home.class);
                startActivity(intent);
            }
        });

    }

    private void updateTotal() {
        double totalPrice = 2500 + 2000; // Accommodation + Travel

        if (sight.isChecked()) {
            totalPrice += 150;
        }
        if (tour.isChecked()) {
            totalPrice += 250;
        }
        if (fine.isChecked()) {
            totalPrice += 250;
        }

        total.setText("Your total is: R"+ String.valueOf(totalPrice));
    }


}

