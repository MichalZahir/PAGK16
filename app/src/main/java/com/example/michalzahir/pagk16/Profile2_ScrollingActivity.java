package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Profile2_ScrollingActivity extends AppCompatActivity {
    String UserName;
    private TextView UserNameTectView;
    private Button newGameButton;
    ImageView ProfilPicture;
    AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2__scrolling);
        UserNameTectView = (TextView) findViewById(R.id.UserNameIcone);

        ProfilPicture = (ImageView) findViewById(R.id.ProfilePic);
        newGameButton = (Button) findViewById(R.id.newGameButton);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logOut();
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }
            });
        }
        String currentUserObjectId = Backendless.UserService.loggedInUser();

        Backendless.UserService.findById  (currentUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
        public void handleResponse(BackendlessUser backendlessUser )
        {

            Backendless.UserService.setCurrentUser(backendlessUser);

            UserName = "" + backendlessUser.getProperty("name");
            UserNameTectView.setText(UserName);


        }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                System.err.println( "Error - " + fault );
            }});
        System.out.println("user id from token " + currentUserObjectId );

        accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null){

            Profile profile = Profile.getCurrentProfile();
            String UserNameFb = profile.getFirstName()+"  "+profile.getLastName();
            UserName =  UserNameFb;
            UserNameTectView.setText(UserName);
            try {


                ProfilPicture.setImageBitmap(new FacebookProfPicture().execute(profile.getId()).get()); //getFacebookProfilePicture(profile.getId()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        newGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        NewGameActivity.class);
                //i.setFlags(16777216);
                startActivity(i);
                finish();


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
