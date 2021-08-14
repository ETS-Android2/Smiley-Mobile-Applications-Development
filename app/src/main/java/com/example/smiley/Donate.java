package com.example.smiley;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Donate extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_donate);

            BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
            bottomNavigationView.setSelectedItemId(R.id.donate);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home:
                            startActivity(new Intent(getApplicationContext(), com.example.smiley.MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;

                        case R.id.donate:
                            return true;

                        case R.id.smile:
                            startActivity(new Intent(getApplicationContext(),Smiley.class));
                            overridePendingTransition(0,0);
                            return true;

                        case R.id.quote:
                            startActivity(new Intent(getApplicationContext(),Quote.class));
                            overridePendingTransition(0,0);
                            return true;


                    }
                    return false;
                }
            });
        }

}


