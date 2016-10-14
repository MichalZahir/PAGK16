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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by michal.zahir on 2016-02-20.
 */
public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    static public ProgressDialog RegisterProgreessDialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        AdView RegisterAdView = (AdView) findViewById(R.id.adViewRegister);
        AdRequest adRequest = new AdRequest.Builder().build();
        RegisterAdView.loadAd(adRequest);
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
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
        // RegisterProgreessDialogue = new ProgressDialog(RegisterActivity.this);
       // RegisterProgreessDialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        RegisterProgreessDialogue = ProgressDialog.show(RegisterActivity.this,"the user registration is being processed... ","Please wait a second ",true);
        MainActivity.LoggedInWithFB = false;
        MainActivity.user.setName(name);
        BackendlessUser user = new BackendlessUser();
        user.setProperty("name", name);
        user.setPassword(password);



        try
        {
            Backendless.UserService.register(user, new BackendlessCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    Log.i("Registration", backendlessUser.getEmail() + " successfully registered");


                    Login(name,password);



                }
                @Override
                public void handleFault(BackendlessFault fault) {
                    RegisterProgreessDialogue.dismiss();
                    Toast.makeText(getApplicationContext(),
                             fault.getMessage() +" Try another user name", Toast.LENGTH_LONG)
                            .show();
                    Log.d(TAG, "User not registered :   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
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
                    MainActivity.user.setName(name);
                    RegisterDeviceUpdateUserDeviceID();
                    // startActivity(i);
                    startActivityForResult(i, 1);

                    finish();



                }
            },true);}
        catch( BackendlessException exception )
        {
            String fault = exception.getCause().toString()+exception.getCode()+exception.getMessage();
            Log.i("Log in Error",fault);
            Toast.makeText(getApplicationContext(),
                    "Login Failed! " + exception.getCause()+"  "+ exception.getMessage(), Toast.LENGTH_LONG)
                    .show();
            RegisterProgreessDialogue.dismiss();

        }

    }
    public void RegisterDeviceUpdateUserDeviceID(){


        String ProjectNumberNotification = "687259024455";
        // TODO: 2016-06-01 Add checking for the device, if registered don't go through the registration.
        Backendless.Messaging.registerDevice(ProjectNumberNotification, "default", new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {
                Log.d(TAG, "Device Registered for backendless messaging and push notifications.   " );
                String Device_ID = Backendless.Messaging.DEVICE_ID;
                Log.d(TAG,"The Device ID is :  "+Device_ID);
                UserUpdatePushNotif.UpdateUserWithDeviceID(Device_ID);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "Device Not Registered .  The Cause :   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
            }
        });



    }
}

