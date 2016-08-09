package com.example.michalzahir.pagk16.fakeActivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.R;
import com.example.michalzahir.pagk16.UserName;
import com.example.michalzahir.pagk16.playerObejtID;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class ActivityFake extends AppCompatActivity {

    private static final String TAG = "ActivityFake " ;
    static AccessToken accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_fake);

        Bundle bundle = this.getIntent().getExtras();
        InitializeObjectIDNotifStart(getApplicationContext());
        SetUserNameoppName(bundle);
        finish();

        com.example.michalzahir.pagk16.Helper.UserQueueQuestionRetriever.RetrieveQuestionForFirstRound(bundle.getString("QuestionIDS"), this);
        //


    }
    public   void SetUserNameoppName(Bundle bundle){
        // TODO: 2016-08-09  when the app entered from the notification the no user object ID.
        if (bundle.containsKey("UserName")){
            MainActivity.userName = new UserName();
            Log.d(TAG, "playerObejtID.getUserObjectID" + playerObejtID.getUserObjectID() + " bundle.get UserNameUSrObjectID  : "+ bundle.get("UserNameUSrObjectID") );
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
    public static void  InitializeObjectIDNotifStart(Context x){
        final String appVersion = "v1";
        Backendless.initApp(x, "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
        FacebookSdk.sdkInitialize(x);

        String userToken = UserTokenStorageFactory.instance().getStorage().get();

        if( userToken != null && !userToken.equals( "" ) )
        {  // user login is available, skip the login activity/login form
            String s =Backendless.UserService.loggedInUser();
            MainActivity.LoggedInWithFB = false;

            playerObejtID.setUserObjectID(s);



        }
        final String currentUserObjectId = Backendless.UserService.loggedInUser();
        if(MainActivity.userName == null) MainActivity.userName = new UserName();
        MainActivity.userName.setUserNameUSrObjectID(currentUserObjectId);
        //fb account if opened from notification
        accessToken = AccessToken.getCurrentAccessToken();
        Log.d(TAG, "accessToken :" + accessToken );
        if( accessToken != null){
            Profile profile = Profile.getCurrentProfile();
            final String UserNameFb = profile.getFirstName()+" "+profile.getLastName();

            com.example.michalzahir.pagk16.Helper.fbUsrStatistics.GetFbUsrStatistics(UserNameFb);
        }

    }
//    @Override
//    protected void onPause(){
//        super.onPause();
//    }
}
