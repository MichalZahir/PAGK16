package com.example.michalzahir.pagk16;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by michal.zahir on 2016-02-20.
 */
public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                ///Test code backendless register one user



                if (!name.isEmpty() && !password.isEmpty()) {
                    // Register user
                  Register(name ,  password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }




            }
        });


        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });



    }
    private void Register(final String name, final String password){

        BackendlessUser user = new BackendlessUser();
        user.setProperty("name", name);
        user.setPassword(password);



        try
        {
            Backendless.UserService.register(user, new BackendlessCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    Log.i("Registration", backendlessUser.getEmail() + " successfully registered");


//                    Intent i = new Intent(getApplicationContext(),
//                            ProfileActivity.class);

                    //i.putExtra ( "name",name);
                    //startActivity(i);
                    Login(name,password);
                    finish();



                }
            });}
        catch( BackendlessException exception )
        {
            String fault = exception.getCause().toString()+exception.getCode()+exception.getMessage();
            Log.i("Registration Error",fault);
            // an error has occurred, the error code can be retrieved with fault.getCode()
        }

    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private  void Login(final String name, final String password){


        try
        {
            Backendless.UserService.login (name,password, new BackendlessCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    Log.i("Loggin in ", backendlessUser.getProperty("name") + " successfully logged in");

                    // session.createLoginSession(name, password);

                    Intent i = new Intent(getApplicationContext(),
                            Profile2_ScrollingActivity.class);
                    i.putExtra("name", name);
                    // startActivity(i);
                    startActivityForResult(i, 1);

                    finish();



                }
            },true);}
        catch( BackendlessException exception )
        {
            String fault = exception.getCause().toString()+exception.getCode()+exception.getMessage();
            Log.i("Log in Error",fault);
            // an error has occurred, the error code can be retrieved with fault.getCode()
        }

    }
}

