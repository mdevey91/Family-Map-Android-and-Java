package com.bignerdranch.android.client;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import SharedFiles.Family;
import SharedFiles.LoginRequest;
import SharedFiles.LoginResult;
import SharedFiles.PersonResult;
import SharedFiles.RegisterRequest;
import SharedFiles.RegisterResult;
import SharedFiles.ServerProxy;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText user_name;
    private EditText server_host;
    private EditText server_port;
    private EditText password;
    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private String gender;

    private String port;
    private String host;

    private RadioButton male;
    private RadioButton female;
    private Button register_button;
    private Button sign_in_button;

    private ServerProxy proxy;
    private RegisterRequest currentUser;
    private Family family;

    public LoginFragment() {
        proxy = new ServerProxy();
        // Required empty public constructor
    }

    public interface DetectChanges {
        void onLoginSuccessful();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Contains all the listeners for the buttons and edit text.
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        user_name = (EditText) view.findViewById(R.id.username);
        user_name.setText("sheila");
        server_host = (EditText) view.findViewById(R.id.server_host);
        server_port = (EditText) view.findViewById(R.id.server_port);
        server_port.setText("8080");
        password = (EditText) view.findViewById(R.id.password);
        password.setText("parker");
        first_name = (EditText) view.findViewById(R.id.first_name);
        last_name = (EditText) view.findViewById(R.id.last_name);
        email = (EditText) view.findViewById(R.id.email);

        male = (RadioButton) view.findViewById(R.id.male_button);
        female = (RadioButton) view.findViewById(R.id.female_button);



        register_button = (Button) view.findViewById(R.id.register);
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                host = server_host.getText().toString();
                port = server_port.getText().toString();
                proxy.setPort(server_port.getText().toString());
                proxy.setServer_host(server_host.getText().toString());
                if(user_name.getText().toString().equals("") || server_host.getText().toString().equals("") || server_port.getText().toString().equals("") || password.getText().toString().equals("") || first_name.getText().toString().equals("") || last_name.getText().toString().equals("") || email.getText().toString().equals("") || (!male.isChecked() && !female.isChecked()))
                    Toast.makeText(getActivity(), "Required fields are blank", Toast.LENGTH_SHORT).show();
                else{
                    if (male.isChecked()) {
                        gender = "m";
                    } else {
                        gender = "f";
                    }
                    RegisterTask task = new RegisterTask();
                    RegisterRequest request = new RegisterRequest(user_name.getText().toString(), password.getText().toString(), email.getText().toString(), last_name.getText().toString(), first_name.getText().toString(), gender);
                    task.execute(request);//pass in RegisterRequest object or a bunch of strings

                }
            }
        });

        sign_in_button = (Button) view.findViewById(R.id.sign_in);
        sign_in_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                host = server_host.getText().toString();
                port = server_port.getText().toString();
                proxy.setPort(server_port.getText().toString());
                proxy.setServer_host(server_host.getText().toString());
                if(user_name.getText().toString().equals("") || server_host.getText().toString().equals("") || server_port.getText().toString().equals("") || password.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "Required fields are blank", Toast.LENGTH_SHORT).show();
                else {
                    LoginTask task = new LoginTask();
                    task.execute(new LoginRequest(user_name.getText().toString(), password.getText().toString()));
//                    sign_in_button.setClickable(true);
//                    register_button.setClickable(true);
                }
            }
        });
        return view;
    }

    private class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {
        @Override
        protected RegisterResult doInBackground(RegisterRequest... params) {
            RegisterResult result = proxy.register(params[0]);
            currentUser = params[0];
            family = Family.getInstance();
            family.Load(currentUser, proxy.getAllPersons(result.getAuthToken()).getData(), proxy.getAllEvents(result.getAuthToken()).getData(), port, host);
            return result;
        }

        @Override
        protected void onPostExecute(RegisterResult result) {
            super.onPostExecute(result);
            //need to call proxy.person() in order to return a toast with the first and last name of the user
            //output a toast to scree
            if(result.getMessage() == null) {
                Toast.makeText(getActivity(), "Welcome " + currentUser.getFirstname() + " " + currentUser.getLastname(), Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onLoginSuccessful();
            }
            else
                Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class LoginTask extends AsyncTask<LoginRequest, String, LoginResult> {
        @Override
        protected LoginResult doInBackground(LoginRequest... params) {
            LoginResult login_result = proxy.login(params[0]);
            if(login_result.getMessage() == null){
                PersonResult person_result = proxy.person(login_result.getPersonId(), login_result.getAuthToken());
                currentUser = new RegisterRequest(login_result.getUserName(), params[0].getPassword(), null, person_result.getFirstname(), person_result.getLastname(), person_result.getGender(), login_result.getPersonId());
                family = Family.getInstance();
                family.Load(currentUser, proxy.getAllPersons(login_result.getAuthToken()).getData(), proxy.getAllEvents(login_result.getAuthToken()).getData(), port, host);
            }
            return login_result;
        }

        @Override
        protected void onPostExecute(LoginResult loginResult) {
            //need to call proxy.person() in order to return a toast with the first and last name of the user
            super.onPostExecute(loginResult);
            if(loginResult.getMessage() == null) {
                Toast.makeText(getActivity(), "Welcome " + currentUser.getFirstname() + " " + currentUser.getLastname(), Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onLoginSuccessful();
            }
            else
                Toast.makeText(getActivity(), loginResult.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
