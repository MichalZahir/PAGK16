package com.example.michalzahir.pagk16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.persistence.local.UserTokenStorageFactory;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button FBLOGIN;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    CallbackManager callbackManager;
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);


         callbackManager  = CallbackManager.Factory.create();
        // Session Manager
        session = new SessionManager(getApplicationContext());
        setContentView(R.layout.activity_main);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        FBLOGIN = (Button) findViewById(R.id.fbLogin);
        //backendless namiary na apke
        final String appVersion = "v1";
        Backendless.initApp(this, "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
        String ProjectNumberNotification = "687259024455";
        // TODO: 2016-06-01 Add checking for the device, if registered don't go through the registration.
        RegisterDeviceUpdateUserDeviceID();




        String userToken = UserTokenStorageFactory.instance().getStorage().get();

        if( userToken != null && !userToken.equals( "" ) )
        {  // user login is available, skip the login activity/login form


            Intent i = new Intent(getApplicationContext(),
                    Profile2_ScrollingActivity.class);
             startActivity(i);

         }
        // token for fb login
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null){
            System.out.println("access token user token faceboook : " + accessToken);
            Profile profile = Profile.getCurrentProfile();

            String UserNameFb = profile.getFirstName()+"  "+profile.getLastName();
            System.out.println(" faceboook UserNameFb  : " + UserNameFb);
            RegisterDeviceUpdateUserDeviceID();
            Intent i = new Intent(getApplicationContext(),
                    Profile2_ScrollingActivity.class);
            startActivity(i);

        }


        // end of token for fb login
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String name = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!name.isEmpty() && !password.isEmpty()) {
                    // Login user
                    Login(name ,  password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);

                startActivity(i);
                finish();
            }
        });
        FBLOGIN.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               fbLogin();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    //makes the profile activity the home activity
                    //i.setFlags(16384);
                    RegisterDeviceUpdateUserDeviceID();

                    i.putExtra("name", name);
                    startActivity(i);
                    //startActivityForResult(i, 1);

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
        }

    }
    private void fbLogin(){
        final Map<String, String> facebookFieldMappings = new HashMap<String, String>();
        facebookFieldMappings.put("name", "name");

        List<String> permissions = new ArrayList<String>();
        permissions.add( "email" );
        permissions.add( "user_friends");
        permissions.add("public_profile");
        permissions.add("user_about_me");

        Backendless.UserService.loginWithFacebookSdk(this,  facebookFieldMappings, permissions, callbackManager,
        new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                // user logged in successfully

                Intent i = new Intent(getApplicationContext(),
                        Profile2_ScrollingActivity.class);
                Profile profile = Profile.getCurrentProfile();


                System.out.println("check the fb backendlsess user : "+ backendlessUser.getObjectId());
                Backendless.UserService.setCurrentUser(backendlessUser);
                String UserNameFb = profile.getFirstName()+"  "+profile.getLastName();
                 i.putExtra ( "name", UserNameFb );
                //startActivity(i);
                startActivityForResult(i, 1);
                finish();

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                // failed to log in
            }
        });
        RegisterDeviceUpdateUserDeviceID();


    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
