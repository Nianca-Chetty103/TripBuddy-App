package com.example.tripbuddy20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    Button memories, gallery,Exit;
    ImageView london, maldives, japan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        japan = findViewById(R.id.japan);
        maldives = findViewById(R.id.maldives);
        london = findViewById(R.id.london);

        memories = findViewById(R.id.memories);
        gallery = findViewById(R.id.gallery);
        Exit = findViewById(R.id.Logout);

        london.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, London.class);
                startActivity(intent);
            }
        });

        japan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Japan.class);
                startActivity(intent);
            }
        });

        maldives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Maldives.class);
                startActivity(intent);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shared = getSharedPreferences("MyAppPref", MODE_PRIVATE);
                SharedPreferences.Editor edit = shared.edit();
                edit.clear();
                edit.apply();

                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
                finish();

            }
        });

        Toolbar hometool = findViewById(R.id.hometool);
        setSupportActionBar(hometool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Home Screen");

       memories.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Home.this, Memories.class);
               startActivity(intent);
           }
       });

       gallery.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Home.this, Gallery.class);
               startActivity(intent);
           }
       });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.bottom_home) {
                return true;
            } else if (id == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                finish();
                return true;
            }
            return false;
        });
    }

    public boolean onCreateOptionsmenu(Menu menu){
        getMenuInflater().inflate(R.menu.homeicons,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.mappin) {
            Toast.makeText(this, "Mappin", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}