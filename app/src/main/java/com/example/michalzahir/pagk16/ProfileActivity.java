package com.example.michalzahir.pagk16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michal.zahir on 2016-02-21.
 */
public class ProfileActivity extends Activity {
    String UserName;
    private TextView UserNameTectView;
    private Button LOGOutButton;
    AccessToken accessToken;
    // Session Manager Class
    SessionManager session;
    CallbackManager callbackManager;
    BackendlessUser currentBackendlessUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.activity_profile);
        UserNameTectView = (TextView) findViewById(R.id.UserNameIcone);

//        session = new SessionManager(getApplicationContext());
        LOGOutButton = (Button) findViewById(R.id.LogOutButton);

//        if (session.checkLogin()){
            // get user data from session
            //HashMap<String, String> user = session.getUserDetails();
            // name
//            String name = user.get(SessionManager.KEY_NAME);
//            String password = user.get(SessionManager.KEY_PASSWORD);
//



        //}
        //else {
          //  if (getCallingActivity ()!=null){
             String currentUserObjectId = Backendless.UserService.loggedInUser();

                        Backendless.UserService.findById  (currentUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
                                                                                                                             public void handleResponse(BackendlessUser backendlessUser )
                        {
                //System.out.println( "[ASYNC] Is login valid? - " + response );
                            Backendless.UserService.setCurrentUser(backendlessUser);

                            UserName = "Welcome Dear  " + backendlessUser.getProperty("name")+ " to PAGK";
                            UserNameTectView.setText(UserName);

                            // currentBackendlessUser = backendlessUser;
                        }

                            @Override
                            public void handleFault( BackendlessFault fault )
                            {
                                System.err.println( "Error - " + fault );
                            }});
                System.out.println("user id from token " + currentUserObjectId );
              //  BackendlessUser user = Backendless.UserService.CurrentUser();

            //}
            //else {
            //            Intent i = new Intent(getApplicationContext(),
            //                    MainActivity.class);
            //        startActivity(i);
            //        finish();
            //}
            //}
         accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null){

            Profile profile = Profile.getCurrentProfile();
            String UserNameFb = profile.getFirstName()+"  "+profile.getLastName();
            UserName = "Welcome Dear  " + UserNameFb+ " to PAGK";
            UserNameTectView.setText(UserName);
        }
        LOGOutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                logOut();
            }
        });
    }


    public void logOut (){
         if( accessToken != null){

             LoginManager.getInstance().logOut();
             Intent i = new Intent(getApplicationContext(),
                     MainActivity.class);
             startActivity(i);
             finish();

         }
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            public void handleResponse(Void response) {
                // user has been logged out.
               // session.logoutUser(); // logout the session with the user info

                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }

            public void handleFault(BackendlessFault fault) {
                System.out.println("cant log out from fb bro sorry!!!!");
                // something went wrong and logout failed, to get the error code call fault.getCode()
            }
        });



    }




}




