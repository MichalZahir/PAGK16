package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.Subscription;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.Message;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PublishStatusEnum;
import com.backendless.messaging.SubscriptionOptions;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.example.michalzahir.pagk16.Helper.GettinQuesQuantityDyn;
import com.example.michalzahir.pagk16.UsersDB.Users;
import com.example.michalzahir.pagk16.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.ads.MobileAds;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.michalzahir.pagk16.MainActivity.LoggedInWithFB;
import static com.example.michalzahir.pagk16.MainActivity.user;
import static com.example.michalzahir.pagk16.MainActivity.userName;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "Caht Activity" ;
    String UserName;
    String UsrsobjIDsTab;
    String UsrsDeviceIDs;
    private EditText history;
    private EditText messageField;
    private TextView chatWithSmbTitleTextView;
    private String subtopic;
    private SubscriptionOptions subscriptionOptions;
    private PublishOptions publishOptions;
    private Subscription subscription;
    Boolean StartFromNotif = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Intent intent = getIntent();
        if (intent.hasExtra("fromNotification")&& user==null){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Started the profile intent while being in the chat activity because of the lack of info.");
                    readyForstartfrmNotifi();
                    System.out.println("After Calling the method readyForstartfrmNotifi ");
//                    Intent profileIntent = new Intent( ChatActivity.this, MainActivity.class );
//                    //profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    System.out.println("Started the profile intent while being in the chat activity because of the lack of info.");
//                    startActivity(profileIntent);
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        UserName = intent.getStringExtra("UsrsNamesTab");
        UsrsobjIDsTab = intent.getStringExtra("UsrsobjIDsTab");
        UsrsDeviceIDs = intent.getStringExtra("UsrsDeviceIDsTab");
        initUI();
        String FstUserName = user.getName();
        if(intent.hasExtra("subtopic")){
            subtopic = intent.getStringExtra("subtopic");
        }
            else {
            subtopic = FstUserName.concat( "_with_" ).concat( UserName);
        }

        publishOptions = new PublishOptions();
        publishOptions.setPublisherId( FstUserName);
        publishOptions.setSubtopic( subtopic );

        subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setSubtopic( subtopic );
        Backendless.Messaging.subscribe( ConstantsClass.DEFAULT_CHANNEL, new AsyncCallback<List<Message>>()
        {
            @Override
            public void handleResponse( List<Message> response )
            {
                onReceiveMessage( response );
            }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText( ChatActivity.this, fault.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        }, subscriptionOptions, new DefaultCallback<Subscription>( ChatActivity.this, "Retrieving subscription" )
        {
            @Override
            public void handleResponse( Subscription response )
            {
                super.handleResponse( response );
                subscription = response;
            }
        } );
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //super.onStop();

        if( subscription != null )
            subscription.cancelSubscription();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if( subscription != null )
            subscription.resumeSubscription();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if( subscription != null )
            subscription.pauseSubscription();
    }
    private void onReceiveMessage( List<Message> messages )
    {



        for( Message message : messages )
        {
            history.setText( history.getText() + "\n" + message.getPublisherId() + ": " + message.getData() );
        }
    }
    private void initUI()
    {
        history = (EditText) findViewById( R.id.historyField );
        messageField = (EditText) findViewById( R.id.messageField );
        chatWithSmbTitleTextView = (TextView) findViewById( R.id.textChatWithSmbTitle );

        chatWithSmbTitleTextView.setText( String.format(   "Waiting for %s to accept invitation..."  , UserName ) );

        messageField.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View view, int keyCode, KeyEvent keyEvent )
            {
                return onSendMessage( keyCode, keyEvent );
            }
        } );
    }
    private boolean onSendMessage( int keyCode, KeyEvent keyEvent )
    {

        if( keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP )
        {
            String message = messageField.getText().toString();

            if( message == null || message.equals( "" ) )
                return true;

            Backendless.Messaging.publish( (Object) message, publishOptions, new DefaultCallback<MessageStatus>( ChatActivity.this, "Sending..." )
            {
                @Override
                public void handleResponse( MessageStatus response )
                {
                    super.handleResponse( response );

                    PublishStatusEnum messageStatus = response.getStatus();

                    if( messageStatus == PublishStatusEnum.SCHEDULED )
                    {
                        messageField.setText( "" );
                    }
                    else
                    {
                        Toast.makeText( ChatActivity.this, "Message status: " + messageStatus.toString(), Toast.LENGTH_SHORT );
                    }
                }
            } );

            return true;
        }
        return false;
    }
    public void  readyForstartfrmNotifi() {
         StartFromNotif = true;
        System.out.println("Start of readyForstartfrmNotifi");
        Context AppContext = getApplicationContext();
        System.out.println("!FacebookSdk.isInitialized()");
        if (!FacebookSdk.isInitialized())
            FacebookSdk.sdkInitialize(AppContext);
        System.out.println("User.getInstance()");
        user = User.getInstance();
        System.out.println("new UserName()");
        userName = new UserName();

        //backendless namiary na apke
        final String appVersion = "v1";
        System.out.println("Backendless.initApp");
        Backendless.initApp(AppContext, "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
        //String ProjectNumberNotification = "687259024455";
        //if (ConstantsClass.QuestionsQuestSize == 0)
          //  ConstantsClass.QuestionsQuestSize = GettinQuesQuantityDyn.GetQuestionsQuantityDynamically();
       // System.out.println("MobileAds.initialize");
        //MobileAds.initialize(AppContext, "ca-app-pub-3940256099942544~3347511713");
        //RegisterDeviceUpdateUserDeviceID();
        System.out.println(" UserTokenStorageFactory.instance().getStorage().get()");
        String userToken = UserTokenStorageFactory.instance().getStorage().get();
        if (userToken != null && !userToken.equals("")) {  // user login is available, skip the login activity/login form
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setContentView(R.layout.activity_splash_screen);
//                        }});

            String s = Backendless.UserService.loggedInUser();
            LoggedInWithFB = false;

            playerObejtID.setUserObjectID(s);
            user.setUserObjectId(s);

        }
        // token for fb login
        System.out.println("AccessToken accessToken = AccessToken.getCurrentAccessToken()  Facebook Access Token");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Log.i("Fb access token  ", accessToken + "");
        if (accessToken != null) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setContentView(R.layout.activity_splash_screen);
//                        }});
            LoggedInWithFB = true;
            System.out.println("access token user token facebook : " + accessToken);
            Profile profile = Profile.getCurrentProfile();
            //String a = AccessToken.getCurrentAccessToken().getUserId();
            final String UserNameFb = profile.getFirstName() + " " + profile.getLastName();
            System.out.println(" facebook UserNameFb  : " + UserNameFb);
            user.setName(UserNameFb);
            MainActivity.userName.setUserName(UserNameFb);
            final String[] UserObjectID = new String[1];

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    UserObjectID[0] = FindUsersObjectID(UserNameFb);

                }
            });

            t.start(); // spawn thread
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            user.setUserObjectId(UserObjectID[0]);
            MainActivity.userName.setUserNameUSrObjectID(UserObjectID[0]);
            playerObejtID.setUserObjectID(UserObjectID[0]);

        }
    }
    public String FindUsersObjectID(String name) {
        String userObjectID = null;
        //final String[] UserObjcetID = new String[1];
        System.out.println(name);
        String whereClause = " name='" + name + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        try {
            BackendlessCollection<Users> BU = Backendless.Persistence.of(Users.class).find(dataQuery);
            for (Users q : BU.getData()) {
                //Users Response = Backendless.Persistence.of(Users.class).findById(q.getObjectId());
                userObjectID = q.getObjectId();

                Profile2_ScrollingActivity.AnsweredQuestonsIds = q.getAnsweredQuestionsIDs();
            }
        } catch (BackendlessException fault) {
            Log.d(TAG, "fault trying to get FB users object ID" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

        }

        return userObjectID;
    }
    @Override
    public void onBackPressed() {
        if (StartFromNotif) {
            Intent i = new Intent(getApplicationContext(),
                    MainActivity.class);

            startActivity(i);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}
