package com.example.michalzahir.pagk16;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.michalzahir.pagk16.ServiceAppOff.MyService;
import com.example.michalzahir.pagk16.fakeActivity.ActivityFake;
import com.facebook.appevents.AppEventsLogger;

public class resultActivity extends AppCompatActivity {
    private static final String TAG = "result Activity";
    private TextView firstUserResultTextView;
    private TextView secondUserResultTextView;
    private  com.example.michalzahir.pagk16.Helper.AutoResizeTextView  firstUserNameTextView;

    private com.example.michalzahir.pagk16.Helper.AutoResizeTextView secondUserNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        firstUserResultTextView = (TextView) findViewById(R.id.firstUserResult);
        secondUserResultTextView = (TextView) findViewById(R.id.secondUserResult);
        firstUserNameTextView = (com.example.michalzahir.pagk16.Helper.AutoResizeTextView) findViewById(R.id.firstUserName);
        secondUserNameTextView = (com.example.michalzahir.pagk16.Helper.AutoResizeTextView) findViewById(R.id.secondUserName);
        Bundle bundle = this.getIntent().getExtras();
        System.out.println("first result from bundle" + bundle.getInt("1st user result") + "      second result from bundle" + bundle.getInt("2nd user result"));
        int intFirstResult;
        int intSecondResult;
        intFirstResult = bundle.getInt("1st user result");
        intSecondResult = bundle.getInt("2nd user result");
        Log.d(TAG, "The result of the first user:" + intFirstResult);
        Log.d(TAG, "The result of the second user:" + intSecondResult);

        if (NewGameActivity.result==null) {
            NewGameActivity.result = new gameResult( );
            NewGameActivity.result.setFirstUSerObjectID(bundle.getString("firstUSerObjectID"));
            NewGameActivity.result.setSecondUSerObjectID(bundle.getString("secondUSerObjectID"));
        }
        if (NewGameActivity.result.getFirstUSerObjectID()==null)NewGameActivity.result.setFirstUSerObjectID(bundle.getString("firstUSerObjectID"));
        if (NewGameActivity.result.getSecondUSerObjectID()==null)NewGameActivity.result.setSecondUSerObjectID(bundle.getString("secondUSerObjectID"));

        NewGameActivity.result.setFirstUserResult(intFirstResult);
        NewGameActivity.result.setSecondtUserResult(intSecondResult);

        firstUserResultTextView.setText(Integer.toString(intFirstResult) + ":");
        secondUserResultTextView.setText(Integer.toString(intSecondResult));
        if(playerObejtID.getUserObjectID()==null)ActivityFake.InitializeObjectIDNotifStart(this);
        SetUserNameoppName(bundle);
        DeslpayUsersName();
        // the last result sent to the second user
        if (bundle.containsKey("Last Result")) {

            NewGameActivity.StopTheGame = NewGameActivity.StopTheGame + 1;
            gameResult.questionsAnswered = gameResult.questionsAnswered +1;
            //second result always sent to the first user
            if(playerObejtID.getUserObjectID()== null ||  playerObejtID.getUserObjectID().isEmpty())
            playerObejtID.setUserObjectID(NewGameActivity.result.getFirstUSerObjectID());
            com.example.michalzahir.pagk16.Helper.wonOrLost.CheckWhoWon(this);
        }
        else {
           if (gameResult.questionsAnswered>=ConstantsClass.QuestionsNumberToBeAsked && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID())){

               gameResult.questionsAnswered =0;
               stopService(new Intent(this, MyService.class));

            new AlertDialog.Builder(this)
                       .setTitle("Your part is done, It's turn for your opponent. ")
                       .setMessage("Please wait for a notification with the last result, please click ok to go to your profile")
                       .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               Intent i = new Intent(getApplicationContext(), MainActivity.class);
                               startActivity(i);
                           }
                       })

                       .setIcon(android.R.drawable.ic_dialog_info)
                       .show();
           }
            else if (gameResult.questionsAnswered>=ConstantsClass.QuestionsNumberToBeAsked&& playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID())){
               stopService(new Intent(this, MyService.class));

               gameResult.questionsAnswered =0;
               endTheGame();
           }

        }

    }
    @Override
    protected void  onStop() {
        super.onStop();
        //playerObejtID.SaveUserObjectIDOnDestroy(getApplicationContext());
        //com.example.michalzahir.pagk16.SavedGames.GamesSaving.SaveGame(this.getClass().getSimpleName());
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onBackPressed() {

    }

    public void SetDialogue() {

        // new Contact instance has been saved
        new AlertDialog.Builder(this)
                .setTitle("Your result : ")
                .setMessage("Your oponent is playing his round right now. Please wait for a notification when he finishes.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    public void endTheGame() {

        // the game is finish go back to your profile save the result in the users table
        if (NewGameActivity.result.getFirstUserResult() > NewGameActivity.result.getSecondtUserResult()) {

            String firstUserObjectId = NewGameActivity.result.getFirstUSerObjectID();
            System.out.println("The winner is  :    " + firstUserObjectId);


            Backendless.UserService.findById(firstUserObjectId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    System.out.println(backendlessUser.getObjectId());
                    int wonGames = (int) backendlessUser.getProperty("WON");
                    backendlessUser.setProperty("WON", wonGames + 1);

                    Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            Log.d(TAG, "The Number of won games is updated of the user :" + user.getUserId());
                        }

                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "The Number of won games Was  not updated because : " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                        }
                    });


                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    System.err.println("Error - " + fault);
                }
            });
            // for the loser
            String secondUserObjectId = NewGameActivity.result.getSecondUSerObjectID();
            Backendless.UserService.findById(secondUserObjectId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    //System.out.println(backendlessUser.getObjectId());
                    int lostGames = (int) backendlessUser.getProperty("LOST");

                    backendlessUser.setProperty("LOST", lostGames + 1);

                    Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            Log.d(TAG, "The Number of lost games is updated of the user :" + user.getUserId());
                        }

                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "The Number of lost games Was  not updated because : " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                        }
                    });


                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    System.err.println("Error - " + fault);
                }
            });


        } else if (NewGameActivity.result.getFirstUserResult() < NewGameActivity.result.getSecondtUserResult()) {
            String secondUserObjectId = NewGameActivity.result.getSecondUSerObjectID();
            System.out.println("The winner is  :    " + secondUserObjectId);


            Backendless.UserService.findById(secondUserObjectId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    System.out.println(backendlessUser.getObjectId());
                    int wonGames = (int) backendlessUser.getProperty("WON");
                    backendlessUser.setProperty("WON", wonGames + 1);

                    Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            Log.d(TAG, "The Number of won games is updated of the user :" + user.getUserId());
                        }

                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "The Number of won games Was  not updated because : " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                        }
                    });


                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    System.err.println("Error - " + fault);
                }
            });


            // for the loser
            String firstUserObjectId = NewGameActivity.result.getFirstUSerObjectID();
            Backendless.UserService.findById(firstUserObjectId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    //System.out.println(backendlessUser.getObjectId());
                    int lostGames = (int) backendlessUser.getProperty("LOST");

                    backendlessUser.setProperty("LOST", lostGames + 1);

                    Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            Log.d(TAG, "The Number of lost games is updated of the user :" + user.getUserId());
                        }

                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "The Number of lost games Was  not updated because : " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                        }
                    });


                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    System.err.println("Error - " + fault);
                }
            });


        } else if (NewGameActivity.result.getFirstUserResult() == NewGameActivity.result.getSecondtUserResult()) {
            String firstUserObjectId = NewGameActivity.result.getFirstUSerObjectID();
            Backendless.UserService.findById(firstUserObjectId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    int drawGames = (int) backendlessUser.getProperty("DRAW");
                    backendlessUser.setProperty("DRAW", drawGames + 1);

                    Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            Log.d(TAG, "The Number of draw games is updated of the user :" + user.getUserId());
                        }

                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "The Number of draw games Was  not updated because : " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                        }
                    });


                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    System.err.println("Error - " + fault);
                }
            });
            String secondUserObjectId = NewGameActivity.result.getSecondUSerObjectID();
            Backendless.UserService.findById(secondUserObjectId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    int drawGames = (int) backendlessUser.getProperty("DRAW");
                    backendlessUser.setProperty("DRAW", drawGames + 1);

                    Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser user) {
                            Log.d(TAG, "The Number of draw games is updated of the user :" + user.getUserId());
                        }

                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "The Number of draw games Was  not updated because : " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                        }
                    });


                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    System.err.println("Error - " + fault);
                }
            });


        }
        //sendTheLastResultNotificationToscndUser();
        com.example.michalzahir.pagk16.Helper.wonOrLost.CheckWhoWon(this);


    }

    public void sendTheLastResultNotificationToscndUser() {
        Bundle resultsBundle = new Bundle();
        resultsBundle.putInt("1st user result", NewGameActivity.result.getFirstUserResult());
        resultsBundle.putInt("2nd user result", NewGameActivity.result.getSecondtUserResult());

        pushNotification.PublishTheLastResultNotificaton(getApplicationContext(), resultsBundle);
    }
    public void DeslpayUsersName(){
        Log.d(TAG, "DeslpayUsersName" + " UserNameUSrObjectID "+MainActivity.userName.getUserNameUSrObjectID() + "  getOponnentUserObjectID  " +MainActivity.userName.getOponnentUserObjectID()  );
        Log.d(TAG, "DeslpayUsersName" + "   getUserName  "+MainActivity.userName.getUserName() + "  getOpponentName  " + MainActivity.userName.getOponnentName());
        Log.d(TAG, "DeslpayUsersName"+"  getFirstUSerObjectID  "+NewGameActivity.result.getFirstUSerObjectID()+"  getSecondUSerObjectID  " +NewGameActivity.result.getSecondUSerObjectID());
        if (MainActivity.userName.getUserNameUSrObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()))
        {
            firstUserNameTextView.setText(MainActivity.userName.getUserName());
            firstUserNameTextView.resizeText();
            if(MainActivity.userName.getOponnentName()!=null){


            secondUserNameTextView.setText(MainActivity.userName.getOponnentName());
                secondUserNameTextView.resizeText();
            }
        }
        else if (MainActivity.userName.getUserNameUSrObjectID().equals(NewGameActivity.result.getSecondUSerObjectID())){
            secondUserNameTextView.setText(MainActivity.userName.getUserName());
            secondUserNameTextView.resizeText();

            if(MainActivity.userName.getOponnentName()!=null) {
                firstUserNameTextView.setText(MainActivity.userName.getOponnentName());
                firstUserNameTextView.resizeText();

            }

        }




    }
    public static void SetUserNameoppName(Bundle bundle){
        if (bundle.containsKey("UserName")){
        MainActivity.userName = new UserName();

            Log.d(TAG, "playerObejtID.getUserObjectID" + playerObejtID.getUserObjectID() + " bundle.get UserNameUSrObjectID  : "+ bundle.get("UserNameUSrObjectID") );



            MainActivity.userName.setUserName(bundle.getString("UserName"));
            MainActivity.userName.setOponnentName(bundle.getString("OpponentName"));
            MainActivity.userName.setUserNameUSrObjectID( bundle.getString("UserNameUSrObjectID"));
            MainActivity.userName.setOponnentUserObjectID(bundle.getString("OpponentUserObjectID"));
    }}
}
