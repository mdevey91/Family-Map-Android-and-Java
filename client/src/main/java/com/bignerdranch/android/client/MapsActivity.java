package com.bignerdranch.android.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MapsActivity extends AppCompatActivity{
    private static final String EVENT_KEY = "event_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Intent intent = getIntent();
//        String person_id = intent.getStringExtra(EVENT_KEY);
        Bundle bundle = getIntent().getExtras();
        MapFragment map_fragment = new MapFragment();
        map_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.map_fragment_container, map_fragment).commit();
    }
}
