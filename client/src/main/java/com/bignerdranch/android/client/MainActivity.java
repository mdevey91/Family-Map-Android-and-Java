package com.bignerdranch.android.client;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoginFragment.DetectChanges{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginFragment login_fragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, login_fragment).commit();
//        Fragment map_fragment = new MapFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, map_fragment).commit();
    }

    @Override
    public void onLoginSuccessful() {
        MapFragment map_fragment = new MapFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, map_fragment).commit();
    }

//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent;
//        switch(item.getItemId()) {
//            case R.id.searchMenuFilter:
//                intent = new Intent(getBaseContext(), ExpandableListActivity.class);
//                startActivity(intent);
//            default:
//        }
//        return true;
//    }
}
