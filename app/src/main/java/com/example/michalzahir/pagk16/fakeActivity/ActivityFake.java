package com.example.michalzahir.pagk16.fakeActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.UserName;
import com.example.michalzahir.pagk16.playerObejtID;

public class ActivityFake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_fake);

        Bundle bundle = this.getIntent().getExtras();
        SetUserNameoppName(bundle);
        finish();

        com.example.michalzahir.pagk16.Helper.UserQueueQuestionRetriever.RetrieveQuestionForFirstRound(bundle.getString("QuestionIDS"), this);
        //


    }
    public   void SetUserNameoppName(Bundle bundle){
        if (bundle.containsKey("UserName")){
            MainActivity.userName = new UserName();
            if (playerObejtID.getUserObjectID().equals(bundle.get("UserNameUSrObjectID"))){
                MainActivity.userName.setUserName(bundle.getString("UserName"));
                MainActivity.userName.setUserNameUSrObjectID(playerObejtID.getUserObjectID());
                MainActivity.userName.setOponnentName(bundle.getString("OpponentName"));
                MainActivity.userName.setOponnentUserObjectID(bundle.getString("OpponentUserObjectID"));
            }
            else if (playerObejtID.getUserObjectID().equals(bundle.get("OpponentUserObjectID"))){
                MainActivity.userName.setUserName(bundle.getString("OpponentName"));
                MainActivity.userName.setOponnentName(bundle.getString("UserName"));
                MainActivity.userName.setUserNameUSrObjectID( bundle.getString("OpponentUserObjectID"));
                MainActivity.userName.setOponnentUserObjectID(bundle.getString("UserNameUSrObjectID"));
            }
        }}
//    @Override
//    protected void onPause(){
//        super.onPause();
//    }
}
