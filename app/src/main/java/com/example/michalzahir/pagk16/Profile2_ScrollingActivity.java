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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.servercode.IBackendlessService;
import com.example.michalzahir.pagk16.SavedGames.SavedGamesActivity;
import com.example.michalzahir.pagk16.Splashes.splashFbLoginActivity;
import com.example.michalzahir.pagk16.model.User;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Profile2_ScrollingActivity extends AppCompatActivity {
    String UserName;
    private TextView UserNameTectView;
    private TextView wonGamesTextView;
    private TextView lostGamesTextView;
    private TextView drawGamesTextView;
    private TextView playedGamesTextView;
    private TextView RankingTextView;
    private TextView pointsTextView;
    private RelativeLayout RankingLayout;
    private Button newGameButton;
    private Button SavedGamesButton;
    int wonGames;
    int lostGames;
    int drawGames;
    int playedGames;
    int Ranking;
    int usersCount;
    int points;
    ImageView ProfilPicture;
    AccessToken accessToken;

    // TODO: 2016-06-28  fix the problem with the late updating of the info on the profile activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2__scrolling);
        final String appVersion = "v1";
        Backendless.initApp(this, "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
        FacebookSdk.sdkInitialize(getApplicationContext());

        MainActivity.user = User.getInstance();
        UserNameTectView = (TextView) findViewById(R.id.UserNameIcone);
        wonGamesTextView = (TextView) findViewById(R.id.tvNumber5);
        lostGamesTextView = (TextView) findViewById(R.id.tvNumber6);
        drawGamesTextView = (TextView) findViewById(R.id.tvNumber1);
        playedGamesTextView = (TextView) findViewById(R.id.tvNumber4);
        SavedGamesButton = (Button) findViewById(R.id.savedGamesButton );
        pointsTextView = (TextView) findViewById(R.id.PointsTextView);
        ProfilPicture = (ImageView) findViewById(R.id.ProfilePic);
        newGameButton = (Button) findViewById(R.id.newGameButton);
        RankingTextView = (TextView) findViewById(R.id.tvNumber3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RankingLayout = (RelativeLayout) findViewById(R.id.RankingLayOut);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Intent intent = getIntent();
        if ( intent.hasExtra("caller")) {
            splashFbLoginActivity.splash.finish();

        }

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
        RankingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile2_ScrollingActivity.this,
                        RankingActivity.class);

                startActivity(i);
                finish();

            }
        });

        final String currentUserObjectId = Backendless.UserService.loggedInUser();
        //MainActivity.user.setUserObjectId(currentUserObjectId);
         Backendless.UserService.findById  (currentUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
        public void handleResponse(BackendlessUser backendlessUser )
        {

            Backendless.UserService.setCurrentUser(backendlessUser);
            playerObejtID.setUserObjectID(currentUserObjectId);
            UserName = "" + backendlessUser.getProperty("name");
            wonGames = (int) backendlessUser.getProperty("WON");
            lostGames = (int) backendlessUser.getProperty("LOST");
            drawGames = (int) backendlessUser.getProperty("DRAW");
            Ranking = (int) backendlessUser.getProperty("RANKING");
            usersCount = (int) backendlessUser.getProperty("usersCount");
            points  = (int) backendlessUser.getProperty("POINTS");
            MainActivity.user.setName(UserName);

            playedGames = wonGames +lostGames+ drawGames;

            UserNameTectView.setText(UserName);
            MainActivity.userName.setUserName(UserName);
            MainActivity.userName.setUserNameUSrObjectID(currentUserObjectId);
            lostGamesTextView.setText(String.valueOf(lostGames));
            drawGamesTextView.setText(String.valueOf(drawGames));
            playedGamesTextView.setText(String.valueOf(playedGames));
            wonGamesTextView.setText(String.valueOf(wonGames));

            RankingTextView.setText(String.valueOf(Ranking)+" from total "+usersCount+" users");
            pointsTextView.setText(String.valueOf(points));

        }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                System.err.println( "Error - Detail " + fault.getDetail() + " Message  "+ fault.getMessage() + fault.getCode() );
            }});
        System.out.println("user id from token " + currentUserObjectId );

        accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null){
            MainActivity.LoggedInWithFB=true;
           // String currentUserObjectIdFB = Backendless.UserService.loggedInUser();
            wonGames =  intent.getIntExtra("wonGames",-1);
            lostGames = intent.getIntExtra("lostGames",-1);
            drawGames = intent.getIntExtra("drawGames",-1);
            playedGames = intent.getIntExtra("playedGames",-1);
            Ranking = intent.getIntExtra("Ranking",-1);
            usersCount = intent.getIntExtra("usersCount",-1);
            points = intent.getIntExtra("points",-1);
            Profile profile = Profile.getCurrentProfile();
            final String UserNameFb = profile.getFirstName()+" "+profile.getLastName();
            if (wonGames ==-1 ){
                final int[][] tab = new int[1][1];

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                       tab[0] =  com.example.michalzahir.pagk16.Helper.fbUsrStatistics.GetFbUsrStatistics(UserNameFb);

                    }});

                t.start(); // spawn thread
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                wonGamesTextView.setText(String.valueOf(tab[0][0]));
                drawGamesTextView.setText(String.valueOf(tab[0][1]));
                lostGamesTextView.setText(String.valueOf(tab[0][2]));
                playedGamesTextView.setText(String.valueOf(tab[0][3]));
                RankingTextView.setText(String.valueOf(tab[0][4])+ " from total "+tab[0][5]+" users");
                pointsTextView.setText(String.valueOf(tab[0][5]));
            }else {
                lostGamesTextView.setText(String.valueOf(lostGames));
                drawGamesTextView.setText(String.valueOf(drawGames));
                playedGamesTextView.setText(String.valueOf(playedGames));
                wonGamesTextView.setText(String.valueOf(wonGames));
                RankingTextView.setText(String.valueOf(Ranking) +" from total "+usersCount+" users");
                pointsTextView.setText(String.valueOf(points));
            }





            UserName =  UserNameFb;
            MainActivity.user.setName(UserNameFb);
            UserNameTectView.setText(UserName);
            MainActivity.userName.setUserName(UserName);
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
        SavedGamesButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SavedGamesActivity.class);
                 startActivity(i);
                finish();


            }
        });



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //playerObejtID.SaveUserObjectIDOnDestroy(getApplicationContext());

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
//        playerObejtID.SaveUserObjectIDOnDestroy(getApplicationContext());


        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
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
    @Override
    public void onBackPressed() {
    }

}
