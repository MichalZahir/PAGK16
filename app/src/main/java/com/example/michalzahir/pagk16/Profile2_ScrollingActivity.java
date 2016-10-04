package com.example.michalzahir.pagk16;


import android.app.ProgressDialog;
import android.content.Intent;

import android.content.res.Configuration;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import com.example.michalzahir.pagk16.SavedGames.SavedGamesActivity;
import com.example.michalzahir.pagk16.Splashes.splashFbLoginActivity;
import com.example.michalzahir.pagk16.model.User;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;




import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


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
    private ImageView RankingArrowImaView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    static public ProgressDialog RankingProgreessDialogue;
    int wonGames;
    int lostGames;
    int drawGames;
    int playedGames;
    int Ranking;
    int OldRanking;
    int usersCount;
    int points;
    String RankingArrow;
    ImageView ProfilPicture;
    AccessToken accessToken;
    public static String AnsweredQuestonsIds;
    public static String OpponentAnsweredQuestonsIds = "";
    AdRequest adRequest;


    // TODO: 2016-06-28  fix the problem with the late updating of the info on the profile activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2__scrolling);

        Thread t = new Thread(new Runnable()  {
            @Override
            public void run() {
                //If there are stories, add them to the table
                final String appVersion = "v1";

                Backendless.initApp(Profile2_ScrollingActivity.this, "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
                if (!FacebookSdk.isInitialized())
                    FacebookSdk.sdkInitialize(getApplicationContext());
                MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

                //AdView ProfileAdView = (AdView) findViewById(R.id.adViewProfile);
                adRequest = new AdRequest.Builder().build();
                //ProfileAdView.loadAd(adRequest);


                MainActivity.user = User.getInstance();


                Intent intent = getIntent();
//        if ( intent.hasExtra("caller")) {
//            splashFbLoginActivity.splash.finish();
//
//        }


                final String currentUserObjectId = Backendless.UserService.loggedInUser();
                //MainActivity.user.setUserObjectId(currentUserObjectId);
                if (currentUserObjectId !=null  && !currentUserObjectId.equals("")){

                    BackendlessUser backendlessUser = Backendless.UserService.findById(currentUserObjectId);

                            Backendless.UserService.setCurrentUser(backendlessUser);
                            playerObejtID.setUserObjectID(currentUserObjectId);
                            UserName = "" + backendlessUser.getProperty("name");
                            wonGames = (int) backendlessUser.getProperty("WON");
                            lostGames = (int) backendlessUser.getProperty("LOST");
                            drawGames = (int) backendlessUser.getProperty("DRAW");
                            Ranking = (int) backendlessUser.getProperty("RANKING");
                            OldRanking = (int) backendlessUser.getProperty("OLDRANKING");
                            usersCount = (int) backendlessUser.getProperty("usersCount");
                            points = (int) backendlessUser.getProperty("POINTS");
                            AnsweredQuestonsIds = (String) backendlessUser.getProperty("AnsweredQuestionsIDs");
                            MainActivity.user.setName(UserName);

//                            if (OldRanking < Ranking) {
//                                RankingArrowImaView.setBackgroundResource(R.drawable.redarraw);
//                            } else if (OldRanking > Ranking)
//                                RankingArrowImaView.setBackgroundResource(R.drawable.greenarrow);
//                            else if (OldRanking == Ranking)
//                                RankingArrowImaView.setBackgroundResource(R.drawable.same);
                            playedGames = wonGames + lostGames + drawGames;

                            // UserNameTectView.setText(UserName);
                            MainActivity.userName.setUserName(UserName);
                            MainActivity.userName.setUserNameUSrObjectID(currentUserObjectId);
//                            lostGamesTextView.setText(String.valueOf(lostGames));
//                            drawGamesTextView.setText(String.valueOf(drawGames));
//                            playedGamesTextView.setText(String.valueOf(playedGames));
//                            wonGamesTextView.setText(String.valueOf(wonGames));
//
//                            RankingTextView.setText(String.valueOf(Ranking) + " from total " + usersCount + " users");
//                            pointsTextView.setText(String.valueOf(points));

                        }



                System.out.println("user id from token " + currentUserObjectId);

                accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken!=null ) {
                    MainActivity.LoggedInWithFB = true;
                    // String currentUserObjectIdFB = Backendless.UserService.loggedInUser();
                    wonGames = intent.getIntExtra("wonGames", -1);
                    lostGames = intent.getIntExtra("lostGames", -1);
                    drawGames = intent.getIntExtra("drawGames", -1);
                    playedGames = intent.getIntExtra("playedGames", -1);
                    Ranking = intent.getIntExtra("Ranking", -1);
                    usersCount = intent.getIntExtra("usersCount", -1);
                    points = intent.getIntExtra("points", -1);
                    RankingArrow = intent.getStringExtra("RANKINGARROW");
                    OldRanking = intent.getIntExtra("OLDRANKING", -1);

                    Profile profile = Profile.getCurrentProfile();
                    final String UserNameFb = profile.getFirstName() + " " + profile.getLastName();
                    if (wonGames == -1) {
                        final int[][] tab = new int[1][1];

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                tab[0] = com.example.michalzahir.pagk16.Helper.fbUsrStatistics.GetFbUsrStatistics(UserNameFb);

                            }
                        });

                        t.start(); // spawn thread
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        wonGames = (tab[0][0]);
                        drawGames = (tab[0][1]);
                        lostGames = (tab[0][2]);
                        playedGames = (tab[0][3]);
                        Ranking = (tab[0][4]);
                        usersCount = (tab[0][5]);
                        points = (tab[0][6]);
                        OldRanking = (tab[0][7]);
//                wonGamesTextView.setText(String.valueOf(tab[0][0]));
//                drawGamesTextView.setText(String.valueOf(tab[0][1]));
//                lostGamesTextView.setText(String.valueOf(tab[0][2]));
//                playedGamesTextView.setText(String.valueOf(tab[0][3]));
//                RankingTextView.setText(String.valueOf(tab[0][4]) + " from total " + tab[0][5] + " users");
//                Ranking = tab[0][4];
//                pointsTextView.setText(String.valueOf(tab[0][6]));
//                OldRanking = tab[0][7];
//                        if (OldRanking < Ranking) {
//                            RankingArrowImaView.setBackgroundResource(R.drawable.redarraw);
//                        } else if (OldRanking > Ranking)
//                            RankingArrowImaView.setBackgroundResource(R.drawable.greenarrow);
//                        else if (OldRanking == Ranking)
//                            RankingArrowImaView.setBackgroundResource(R.drawable.same);

                    }
                    //else {
//                        lostGamesTextView.setText(String.valueOf(lostGames));
//                        drawGamesTextView.setText(String.valueOf(drawGames));
//                        playedGamesTextView.setText(String.valueOf(playedGames));
//                        wonGamesTextView.setText(String.valueOf(wonGames));
//                        RankingTextView.setText(String.valueOf(Ranking) + " from total " + usersCount + " users");
//                        pointsTextView.setText(String.valueOf(points));
//                        if (OldRanking < Ranking) {
//                            RankingArrowImaView.setBackgroundResource(R.drawable.redarraw);
//                        } else if (OldRanking > Ranking)
//                            RankingArrowImaView.setBackgroundResource(R.drawable.greenarrow);
//                        else if (OldRanking == Ranking)
//                            RankingArrowImaView.setBackgroundResource(R.drawable.same);
                    //   }


                    UserName = UserNameFb;
                    MainActivity.user.setName(UserNameFb);
//                    UserNameTectView.setText(UserName);
                    MainActivity.userName.setUserName(UserName);
                }


                }

        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // code runs in a thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (RegisterActivity.RegisterProgreessDialogue != null)
                        RegisterActivity.RegisterProgreessDialogue.dismiss();
                    if (MainActivity.FBLoginProgreessDialogue != null)
                        MainActivity.FBLoginProgreessDialogue.dismiss();
                    UserNameTectView = (TextView) findViewById(R.id.UserNameIcone);
                    wonGamesTextView = (TextView) findViewById(R.id.tvNumber5);
                    lostGamesTextView = (TextView) findViewById(R.id.tvNumber6);
                    drawGamesTextView = (TextView) findViewById(R.id.tvNumber1);
                    playedGamesTextView = (TextView) findViewById(R.id.tvNumber4);
                    SavedGamesButton = (Button) findViewById(R.id.savedGamesButton);
                    pointsTextView = (TextView) findViewById(R.id.PointsTextView);
                    ProfilPicture = (ImageView) findViewById(R.id.ProfilePic);
                    newGameButton = (Button) findViewById(R.id.newGameButton);
                    RankingTextView = (TextView) findViewById(R.id.tvNumber3);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    RankingArrowImaView = (ImageView) findViewById(R.id.ivContactItem3);

                    setSupportActionBar(toolbar);
                    RankingLayout = (RelativeLayout) findViewById(R.id.RankingLayOut);
                    //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    lostGamesTextView.setText(String.valueOf(lostGames));
                    drawGamesTextView.setText(String.valueOf(drawGames));
                    playedGamesTextView.setText(String.valueOf(playedGames));
                    wonGamesTextView.setText(String.valueOf(wonGames));
                    RankingTextView.setText(String.valueOf(Ranking) + " from total " + usersCount + " users");
                    pointsTextView.setText(String.valueOf(points));
                    if (OldRanking < Ranking) {
                        RankingArrowImaView.setBackgroundResource(R.drawable.redarraw);
                    } else if (OldRanking > Ranking)
                        RankingArrowImaView.setBackgroundResource(R.drawable.greenarrow);
                    else if (OldRanking == Ranking)
                        RankingArrowImaView.setBackgroundResource(R.drawable.same);
                    UserNameTectView.setText(UserName);

                    AdView mAdView = (AdView) findViewById(R.id.adView);
                    if (resultActivity.mInterstitialAd == null) {
                        resultActivity.mInterstitialAd = new InterstitialAd(Profile2_ScrollingActivity.this);
                        resultActivity.mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

                    }
                    if (resultActivity.mInterstitialAd != null && !resultActivity.mInterstitialAd.isLoading() && !resultActivity.mInterstitialAd.isLoaded()) {
                        resultActivity.loadInterstitialAd(Profile2_ScrollingActivity.this);
                    }
                    mAdView.loadAd(adRequest);


//                    if (fab != null) {
//                        fab.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                logOut();
////                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                            .setAction("Action", null).show();
//                            }
//                        });
//                    }
                    RankingLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RankingProgreessDialogue = new ProgressDialog(Profile2_ScrollingActivity.this);
                            RankingProgreessDialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            RankingProgreessDialogue = ProgressDialog.show(Profile2_ScrollingActivity.this, "Loading data.... ", "Please wait, it might take a minute to load the data ", true);


                            Intent i = new Intent(Profile2_ScrollingActivity.this,
                                    RankingActivity.class);

                            startActivity(i);
                            finish();

                        }
                    });
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
                            RankingProgreessDialogue = new ProgressDialog(Profile2_ScrollingActivity.this);
                            RankingProgreessDialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            RankingProgreessDialogue = ProgressDialog.show(Profile2_ScrollingActivity.this, "Loading data.... ", "Please wait a second until we load data ", true);
                            Intent i = new Intent(getApplicationContext(),
                                    SavedGamesActivity.class);
                            startActivity(i);
                            finish();


                        }
                    });



                    toolbar.setNavigationIcon(R.drawable.logout2);
                    toolbar.setNavigationContentDescription("Logout");

                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Toast.makeText(Profile2_ScrollingActivity.this, "asd"+"clicked on menu" , Toast.LENGTH_SHORT).show();
                            logOut();
                        }

                    });


//                    if (savedInstanceState == null) {
//                        selectItem(0);
//                    }




                }
            });
        } catch (final Exception ex) {
            Log.i("---", "Exception in thread");
        }


//            try {
//
//
//                ProfilPicture.setImageBitmap(new FacebookProfPicture().execute(profile.getId()).get()); //getFacebookProfilePicture(profile.getId()));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }

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

    public void logOut() {
        if (accessToken != null) {

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

