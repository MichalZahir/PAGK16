package com.example.michalzahir.pagk16;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.example.michalzahir.pagk16.RankingActivity.RankingGame;

public class RankingProfileActivityTemp extends AppCompatActivity {
    ImageView ProfilPicture;
    static public gameResult result;
    Bitmap bitmap = null;
    private TextView UserNameTectView;
    private Button playButton;
    private Button chatButton;
    String UserName;
    String UsrsobjIDsTab;
    String AnsweredQuestionsIds;
    String UsrsDeviceIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_temp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ProfilPicture = (ImageView) findViewById(R.id.ProfilePicRanking);
        UserNameTectView = (TextView) findViewById(R.id.UserNameIconeRanking);
        playButton = (Button) findViewById(R.id.playButton);
        chatButton= (Button) findViewById(R.id.chatButton);
        Intent intent = getIntent();

        final String FacebookID = intent.getStringExtra("FbProfileID");
        UserName = intent.getStringExtra("UsrsNamesTab");
        UsrsobjIDsTab = intent.getStringExtra("UsrsobjIDsTab");
        AnsweredQuestionsIds = intent.getStringExtra("AnsweredQuestionsIds");
        UsrsDeviceIDs = intent.getStringExtra("UsrsDeviceIDsTab");
        UserNameTectView.setText(UserName);
        blockButons(UsrsobjIDsTab);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String imageURL;

                imageURL = "https://graph.facebook.com/" + FacebookID + "/picture?type=large";
                InputStream in = null;
                try {
                    in = (InputStream) new URL(imageURL).getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(in);
                ProfilPicture.setImageBitmap(bitmap);

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ProfilPicture.setImageBitmap(new FacebookProfPicture().execute(profile.getId()).get()); //getFacebookProfilePicture(profile.getId()));
        ProfilPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //actions

                ProfilPicture.setScaleType(ImageView.ScaleType.FIT_XY);

            }

        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 MainActivity.userName.setOponnentName(UserName);
                    RankingGame = true;

                    result = new gameResult();
                    MainActivity.userName.setOponnentUserObjectID(UsrsobjIDsTab);
                    result.setSecondUSerObjectID(UsrsobjIDsTab);
                    Profile2_ScrollingActivity.OpponentAnsweredQuestonsIds = AnsweredQuestionsIds;

                    result.setFirstUSerObjectID(MainActivity.user.getUserObjectId());
                    result.setFirstUserResult(0);
                    result.setSecondtUserResult(0);
                    NewGameActivity.result = result;
                    NewGameActivity.AddUserToQueue = false;
                    fbFriendsListActivity.FbGame=false;
                    pushNotification.OpponentDeviceID= UsrsDeviceIDs;

                    playerObejtID.setUserObjectID(MainActivity.user.getUserObjectId());
                    gameResult.questionsAnswered =0;

                    com.example.michalzahir.pagk16.gettingQuestions.getQuestions(RankingProfileActivityTemp.this);



            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RankingProfileActivityTemp.this, "This Action"+" will be available in the next release" , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RankingProfileActivityTemp.this,
                        ChatActivity.class);
                i.putExtra("UsrsDeviceIDsTab",UsrsDeviceIDs);
                i.putExtra("UsrsobjIDsTab",UsrsobjIDsTab);
                i.putExtra("UsrsNamesTab",UserName);
                startActivity(i);

            }
        });



    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }
    public void blockButons(String UserObjectID){

        if (playerObejtID.getUserObjectID().equals(UserObjectID)){

            chatButton.setEnabled(false);
            playButton.setEnabled(false);
        }
    }
}
