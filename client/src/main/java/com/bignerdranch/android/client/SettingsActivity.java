package com.bignerdranch.android.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import SharedFiles.Family;
import SharedFiles.FiltersAndSettings;
import SharedFiles.LoginRequest;
import SharedFiles.LoginResult;
import SharedFiles.PersonResult;
import SharedFiles.RegisterRequest;
import SharedFiles.ServerProxy;

public class SettingsActivity extends AppCompatActivity {
    private ServerProxy proxy = new ServerProxy(Family.getInstance().getServerHost(), Family.getInstance().getServerPort());


    private String[] spinner_colors;
    private String[] map_types;

    private Spinner life_story_spinner;
    private Spinner family_tree_spinner;
    private Spinner spouse_spinner;
    private Spinner map_spinner;

    private LinearLayout re_sync_button;
    private LinearLayout logout_button;

    private Switch life_story_switch;
    private Switch family_tree_switch;
    private Switch spouse_switch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FiltersAndSettings settings = FiltersAndSettings.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_colors = getResources().getStringArray(R.array.colors);
        map_types = getResources().getStringArray(R.array.map_style);

        life_story_spinner = (Spinner)findViewById(R.id.life_story_color);
        family_tree_spinner = (Spinner)findViewById(R.id.family_tree_color);
        spouse_spinner = (Spinner)findViewById(R.id.spouse_color);
        map_spinner = (Spinner)findViewById(R.id.map_type);
        re_sync_button = (LinearLayout)findViewById(R.id.re_sync_data);
        logout_button = (LinearLayout)findViewById(R.id.logout);
        family_tree_switch = (Switch)findViewById(R.id.family_tree_switch);
        life_story_switch = (Switch)findViewById(R.id.life_story_switch);
        spouse_switch = (Switch)findViewById(R.id.spouse_switch);

        spouse_switch.setChecked(settings.isSpouseBool());
        life_story_switch.setChecked(settings.isLifeStoryBool());
        family_tree_switch.setChecked(settings.isFamilyTreeBool());

        spouse_spinner.setSelection(getIndex(spouse_spinner, settings.getSpouseLineColor()));
        life_story_spinner.setSelection(getIndex(life_story_spinner, settings.getLifeStoryLinesColor()));
        family_tree_spinner.setSelection(getIndex(family_tree_spinner, settings.getFamilyTreeLineColor()));
        map_spinner.setSelection(getIndex(map_spinner, settings.getMapType()));

        logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Family.getInstance().clear();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //Switches
        life_story_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setLifeStoryBool(isChecked);
            }
        });
        family_tree_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setFamilyTreeBool(isChecked);
            }
        });
        spouse_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setSpouseBool(isChecked);
            }
        });

        //Spinners
        life_story_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Toast.makeText(getBaseContext(), "You must select a color!", Toast.LENGTH_SHORT).show();
                } else {
                    settings.setLifeStoryLinesColor(spinner_colors[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spouse_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Toast.makeText(getBaseContext(), "You must select a color!", Toast.LENGTH_SHORT).show();
                } else {
                    settings.setSpouseLineColor(spinner_colors[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        family_tree_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Toast.makeText(getBaseContext(), "You must select a color!", Toast.LENGTH_SHORT).show();
                } else {
                    settings.setFamilyTreeLineColor(spinner_colors[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        map_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.setMapType(map_types[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        re_sync_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ReSyncTask task = new ReSyncTask();
                task.execute(new LoginRequest(Family.getInstance().getRegisterRequest().getUsername(), Family.getInstance().getRegisterRequest().getPassword()));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
        }
        return true;
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    private class ReSyncTask extends AsyncTask<LoginRequest, String, LoginResult> {
        Family family = Family.getInstance();
        RegisterRequest currentUser = family.getRegisterRequest();
        @Override
        protected LoginResult doInBackground(LoginRequest... params) {
            LoginResult login_result = proxy.login(params[0]);
            if(login_result.getMessage() == null){
                PersonResult person_result = proxy.person(login_result.getPersonId(), login_result.getAuthToken());
                currentUser = new RegisterRequest(login_result.getUserName(), params[0].getPassword(), null, person_result.getFirstname(), person_result.getLastname(), person_result.getGender(), login_result.getPersonId());
                String port = family.getServerPort();
                String host = family.getServerHost();
                Family.getInstance().clear();
                Family.getInstance().Load(currentUser, proxy.getAllPersons(login_result.getAuthToken()).getData(), proxy.getAllEvents(login_result.getAuthToken()).getData(), port, host);
            }
            return login_result;
        }

        @Override
        protected void onPostExecute(LoginResult loginResult) {
            //need to call proxy.person() in order to return a toast with the first and last name of the user
            super.onPostExecute(loginResult);
            if(loginResult.getMessage() == null) {
                Toast.makeText(getBaseContext(), "Re-sync was successful", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getBaseContext(), "Re-sync was unsuccessful", Toast.LENGTH_SHORT).show();
        }

    }
}

