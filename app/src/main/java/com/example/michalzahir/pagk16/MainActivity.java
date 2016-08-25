package com.example.michalzahir.pagk16;

import android.app.ProgressDialog;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.BackendlessCollection;
import com.backendless.Messaging;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.example.michalzahir.pagk16.UsersDB.Users;
import com.example.michalzahir.pagk16.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    int wonGames;
    int lostGames;
    int drawGames;
    int playedGames;
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
    public static UserName userName;
    public static User user;
    public static boolean LoggedInWithFB;
    int fbWon;
    int fbLost;
    int fbDraw;
    int fbplayed;
    int fbRanking;
    int usersCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        user = User.getInstance();
        userName = new UserName();
        callbackManager  = CallbackManager.Factory.create();
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
            String s =Backendless.UserService.loggedInUser();
            LoggedInWithFB = false;

            playerObejtID.setUserObjectID(s);
            user.setUserObjectId(s);
            Intent i = new Intent(getApplicationContext(),
                    Profile2_ScrollingActivity.class);
             startActivity(i);

         }
        // token for fb login
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Log.i("Fb access token  ", accessToken   +""  );
        if( accessToken != null){
            LoggedInWithFB = true;
            System.out.println("access token user token facebook : " + accessToken);
            Profile profile = Profile.getCurrentProfile();
            //String a = AccessToken.getCurrentAccessToken().getUserId();
            final String UserNameFb = profile.getFirstName()+" "+profile.getLastName();
            System.out.println(" facebook UserNameFb  : " + UserNameFb);
            user.setName(UserNameFb);
            MainActivity.userName.setUserName(UserNameFb);
            final String[] UserObjectID = new String[1];

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                      UserObjectID[0] = FindUsersObjectID(UserNameFb);

                }});

            t.start(); // spawn thread
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            user.setUserObjectId(UserObjectID[0]);
            playerObejtID.setUserObjectID(UserObjectID[0]);
//            String s = Backendless.UserService.loggedInUser();
//            playerObejtID.setUserObjectID(s);

            RegisterDeviceUpdateUserDeviceID();
            Intent i = new Intent(getApplicationContext(),
                    Profile2_ScrollingActivity.class);
            i.putExtra("wonGames",fbWon);
            i.putExtra("lostGames",fbLost);
            i.putExtra("drawGames",fbDraw);
            i.putExtra("playedGames",fbplayed);
            i.putExtra("Ranking",fbRanking);
            i.putExtra("usersCount",  usersCount);
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


        // hash key for fb

        try {
            PackageInfo info =   getApplicationContext().getPackageManager().getPackageInfo(
                    "com.example.michalzahir.pagk16",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

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
//        playerObejtID.SaveUserObjectIDOnDestroy(getApplicationContext());


        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //playerObejtID.SaveUserObjectIDOnDestroy(getApplicationContext());

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

        LoggedInWithFB = false;
        try
        {
            Backendless.UserService.login (name,password, new BackendlessCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    Log.i("Loggin in ", backendlessUser.getProperty("name") + " successfully logged in");
                    user.setName(name);
                    MainActivity.userName.setUserName((String) backendlessUser.getProperty("name"));
                   // session.createLoginSession(name, password);
                    playerObejtID.setUserObjectID(backendlessUser.getObjectId());
                    MainActivity.userName.setUserNameUSrObjectID(backendlessUser.getObjectId());
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
                public void handleFault( BackendlessFault fault )
                {
                    // login failed, to get the error code call fault.getCode()
                    Log.d(TAG, "The login failed because : " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                    Toast.makeText(getApplicationContext(),
                            fault.getMessage()+"           Please enter the correct credentials! " +
                                    "Or click the Not a member? Sign me up now, button if you don't have an account.", Toast.LENGTH_LONG)
                            .show();
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
        LoggedInWithFB = true;
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
                wonGames = (int) backendlessUser.getProperty("WON");
                MainActivity.userName.setUserName((String) backendlessUser.getProperty("name"));
                lostGames = (int) backendlessUser.getProperty("LOST");
                drawGames = (int) backendlessUser.getProperty("DRAW");
                fbRanking = (int) backendlessUser.getProperty("RANKING");
                usersCount = (int) backendlessUser.getProperty("usersCount");
                user.setUserObjectId(backendlessUser.getObjectId());
                MainActivity.userName.setUserNameUSrObjectID(backendlessUser.getObjectId());
                playedGames = wonGames +lostGames+ drawGames;
                i.putExtra ( "wonGames", wonGames );
                i.putExtra ( "lostGames", lostGames );
                i.putExtra ( "drawGames", drawGames );
                i.putExtra ( "playedGames", playedGames );
                i.putExtra("Ranking",fbRanking);
                i.putExtra("usersCount",  usersCount);
                System.out.println("check the fb backendlsess user : "+ backendlessUser.getObjectId());
                Backendless.UserService.setCurrentUser(backendlessUser);
                final String currentUserObjectId = backendlessUser.getObjectId();
                playerObejtID.setUserObjectID(currentUserObjectId);
                String ProjectNumberNotification = "687259024455";
                // TODO: 2016-06-01 Add checking for the device, if registered don't go through the registration.
                // TODO: 2016-06-14 Add the user object id to the playerObjectID when logging in with facebook or when loggin in with the token of facebook.
                Backendless.Messaging.registerDevice(ProjectNumberNotification, "default", new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Log.d(TAG, "Device Registered for backendless messaging and push notifications.   " );
                        final String Device_ID = Messaging.DEVICE_ID;
                        Log.d(TAG,"The Device ID is :  "+Device_ID);
                       // UserUpdatePushNotif.UpdateUserWithDeviceID(Device_ID);
                        user.setDeviceID(Device_ID);
                        //Backendless.UserService.loggedInUser();
                        System.out.println("the current user for fb users :    " + currentUserObjectId);


                        Backendless.UserService.findById  (currentUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
                        public void handleResponse(BackendlessUser backendlessUser )
                        {

                            System.out.println(backendlessUser.getObjectId());
                            backendlessUser.setProperty("Device_ID", Device_ID);

                            Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                                public void handleResponse(BackendlessUser user) {
                                    Log.d(TAG, "The Device ID is updated succeffully for the user  :" + user.getUserId());
                                }

                                public void handleFault(BackendlessFault fault) {
                                    Log.d(TAG, "User not updated (Device ID Update ) for the reasons" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                                }
                            });


                        }

                            @Override
                            public void handleFault( BackendlessFault fault )
                            {
                                System.err.println( "Error - " + fault );
                            }});



                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d(TAG, "Device Not Registered .  The Cause :   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
                    }
                });
                String UserNameFb = profile.getFirstName()+"  "+profile.getLastName();
                 i.putExtra ( "name", UserNameFb );
                //startActivity(i);
                startActivityForResult(i, 1);
                finish();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "Failed to loggin with Facebook .  The Cause :   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
            }
        });
        //RegisterDeviceUpdateUserDeviceID();


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
                String Device_ID = Messaging.DEVICE_ID;
                Log.d(TAG,"The Device ID is :  "+Device_ID);
                UserUpdatePushNotif.UpdateUserWithDeviceID(Device_ID);
                user.setDeviceID(Device_ID);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "Device Not Registered .  The Cause :   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
            }
        });



    }
    public String FindUsersObjectID(String name){
        String userObjectID = null;
        //final String[] UserObjcetID = new String[1];
        System.out.println(name);
        String whereClause = " name='" + name+"'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        try {
            BackendlessCollection<Users> BU = Backendless.Persistence.of(Users.class).find(dataQuery);
            for (Users q : BU.getData()) {
                //Users Response = Backendless.Persistence.of(Users.class).findById(q.getObjectId());
                userObjectID = q.getObjectId();
                fbWon = q.getWON();
                fbDraw = q.getDRAW();
                fbLost = q.getLOST();
                fbplayed = fbDraw + fbLost + fbWon;
                fbRanking = q.getRANKING();
                usersCount = q.getUsersCount();
            }
        }
        catch (BackendlessException fault){
            Log.d(TAG, "fault trying to get FB users object ID" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

        }
//        Backendless.Persistence.of (Users.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Users>>() {
//            @Override
//            public void handleResponse(BackendlessCollection<Users> foundQuestions) {
//                for (Users q : foundQuestions.getData()) {
//                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
//                    Backendless.Persistence.of(Users.class).findById(q.getObjectId(), new AsyncCallback<Users>() {
//                        @Override
//                        public void handleResponse(Users response) {
//                            Log.d(TAG, "Success trying to fetch FB user object ID using hte name only : the user's object ID" + response.getObjectId()+" The user's Device ID : " +response.getDevice_ID());
//                            //result.setSecondUSerObjectID(response.getObjectId());
//                            UserObjcetID[0] = response.getObjectId();
//                        }
//
//                        @Override
//                        public void handleFault(BackendlessFault fault) {
//                            Log.d(TAG, "fault trying to fetch FB user object ID using hte name only" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
//
//            }
//        });
       return userObjectID;
    }


}
