package com.example.michalzahir.pagk16;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PushBroadcastMask;
import com.backendless.messaging.PushPolicyEnum;
import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.Helper.USERS_QUEUE;
import com.example.michalzahir.pagk16.SavedGames.GamesSaving;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import weborb.client.Responder;

public class NewGameActivity extends AppCompatActivity {
    private Button newFBGameButton;
    private Button newRandomGameButton;
    static public gameResult result;
    private static final String TAG = NewGameActivity.class.getSimpleName();
    public static Boolean yourTurnToChooseCategory = false;
    public static int StopTheGame = 0;
    public static String UserQueueObjectID;
    static Boolean AddUserToQueue;
    int QuestionID;
    String QuestionsIDSArray;
    String QuestionCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        newFBGameButton = (Button) findViewById(R.id.fbNewGameButton);
        newRandomGameButton = (Button) findViewById(R.id.RandomNewGameButton);
        result = new gameResult();
        StopTheGame = 0;
        result.setFirstUserResult(0);
        result.setSecondtUserResult(0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Boolean[] x = new Boolean[1];
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
        newRandomGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                fbFriendsListActivity.FbGame = false;
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        x[0] = SearchForAnOppenent();
                    }
                });

                t.start(); // spawn thread

                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if (!x[0]) {
                    com.example.michalzahir.pagk16.Helper.UserQueueQuestionRetriever.RetrieveQuestionForFirstRound(QuestionsIDSArray , getApplicationContext());

                } else if (x[0]) {
                    result.setFirstUSerObjectID(playerObejtID.getUserObjectID());
                    result.setFirstUserResult(0);
                    result.setSecondtUserResult(0);
                    gettingQuestions.getQuestions(getApplicationContext());
//                    Intent i = new Intent(getApplicationContext(),
//                            categoryChoiceActivity.class);
//                    startActivity(i);

                }


            }
        });


        newFBGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                 if (!MainActivity.LoggedInWithFB) {
                    SetDialogueForNotFbLoggedusr();
                } else
                    com.example.michalzahir.pagk16.FacebookUsers.fbFriendsList.getFriendList(getApplicationContext());


            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                Profile2_ScrollingActivity.class);
        //i.setFlags(16777216);
        startActivity(i);
        finish();
    }

    Boolean SearchForAnOppenent() {
        //AddUserToQueue=true;
        USERS_QUEUE lastUserInQueue = null;
        try {
            lastUserInQueue = Backendless.Persistence.of(USERS_QUEUE.class).findLast();

        } catch (BackendlessException e) {
            AddUserToQueue = true;
            e.printStackTrace();
        }
        //AddUserToQueue=false;
        if (lastUserInQueue != null) {
            Log.d(TAG, "Fetching a user from the users queue was success  ");
            NewGameActivity.result.setFirstUSerObjectID(lastUserInQueue.getUser_object_ID());
            NewGameActivity.result.setSecondUSerObjectID(playerObejtID.getUserObjectID());
            NewGameActivity.result.setFirstUserResult(lastUserInQueue.getResult());
            NewGameActivity.result.setSecondtUserResult(0);
            QuestionID = lastUserInQueue.getUser_Question_ID();

            QuestionsIDSArray = lastUserInQueue.getQuestionIDSArray();
            Log.d("Question IDS Array",QuestionsIDSArray);
            QuestionCategory = lastUserInQueue.getUser_Question_Category();
            AddUserToQueue = false;
            com.example.michalzahir.pagk16.Helper.user_Queue_Deleter.DeleteOponent(lastUserInQueue);
        } else if (lastUserInQueue != null) AddUserToQueue = true;

//        Backendless.Persistence.of(USERS_QUEUE.class).find(  new AsyncCallback<BackendlessCollection<USERS_QUEUE>>() {
//            @Override
//            public void handleResponse(BackendlessCollection<USERS_QUEUE> foundOponent) {
//                for (USERS_QUEUE q : foundOponent.getData()) {
//                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
//                    Backendless.Persistence.of(USERS_QUEUE.class).findById(q.getObjectId(), new AsyncCallback<USERS_QUEUE>() {
//
//                        @Override
//                        public void handleResponse(USERS_QUEUE response) {
//
//
//                            Log.d(TAG, "Fetching a user from the users queue was success  " );
//                            AddUserToQueue=false;
//                            NewGameActivity.result.setFirstUSerObjectID(response.getUser_object_ID());
//                            NewGameActivity.result.setSecondUSerObjectID(playerObejtID.getUserObjectID());
//                            NewGameActivity.result.setFirstUserResult(response.getResult());
//                            QuestionID = response.getUser_Question_ID();
//                            QuestionCategory = response.getUser_Question_Category();
//                            com.example.michalzahir.pagk16.Helper.user_Queue_Deleter.DeleteOponent(response);
//
//                        }
//
//                        @Override
//                        public void handleFault(BackendlessFault fault) {
//                            AddUserToQueue=true;
//                            Log.d(TAG, "Found data in the user queue but was not able to retrieve info about a specific user" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                AddUserToQueue=true;
//                Log.d(TAG, "empty table : fault trying to fetch The user from the User Queue " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
//
//            }
//        });


        return AddUserToQueue;
    }

    public void SetDialogueForNotFbLoggedusr() {

        new AlertDialog.Builder(this)
                .setTitle("You're not logged in With your Facebook account.")
                .setMessage("Please click OK to go to Your profile, you can log out and sign in with your facebook account to be able to play with facebook friends.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent i = new Intent(getApplicationContext(), Profile2_ScrollingActivity.class);
                        startActivity(i);


                    }
                })

                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

}
