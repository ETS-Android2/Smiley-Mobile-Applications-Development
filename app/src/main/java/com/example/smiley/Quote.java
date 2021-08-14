package com.example.smiley;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Quote extends AppCompatActivity {
    int test = Smiley.counter;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        String quotes[] = getResources().getStringArray(R.array.Quote);
        String author[] = getResources().getStringArray(R.array.Author);

        ArrayList<QuoteItem> quoteList = new ArrayList<>();
        for(int i = 0; i<test; i++){
            quoteList.add(new QuoteItem(R.drawable.ic_baseline_format_quote_24,quotes[i], author[i]));
        }



        mRecyclerView = findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new com.example.smiley.QuoteAdapter(quoteList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.quote);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.donate:
                        startActivity(new Intent(getApplicationContext(), Donate.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.smile:
                        startActivity(new Intent(getApplicationContext(), Smiley.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.quote:
                        return true;


                }
                return false;
            }
        });



    }
}
