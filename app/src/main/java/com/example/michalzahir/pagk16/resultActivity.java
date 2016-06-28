package com.example.michalzahir.pagk16;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class resultActivity extends AppCompatActivity {
    private static final String TAG = "result Activity";
    private TextView firstUserResultTextView;
    private TextView secondUserResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        firstUserResultTextView = (TextView) findViewById(R.id.firstUserResult );
        secondUserResultTextView = (TextView) findViewById(R.id.secondUserResult);
        Bundle bundle = this.getIntent().getExtras();
        System.out.println("first result from bundle"+bundle.getInt("1st user result")+"      second result from bundle"+bundle.getInt("2nd user result"));
        int intFirstResult;
        int intSecondResult;
        intFirstResult = bundle.getInt("1st user result");
        intSecondResult = bundle.getInt("2nd user result");
        Log.d(TAG, "The result of the first user:" + intFirstResult);
        Log.d(TAG, "The result of the second user:" + intSecondResult);
        NewGameActivity.result.setFirstUserResult(intFirstResult);
        NewGameActivity.result.setSecondtUserResult(intSecondResult);

        firstUserResultTextView.setText(Integer.toString(intFirstResult) + ":");
        secondUserResultTextView.setText(Integer.toString(intSecondResult));
        NewGameActivity.StopTheGame = NewGameActivity.StopTheGame +1;
        if (NewGameActivity.StopTheGame >=  ConstantsClass.QuestionsNumberToBeAsked && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()))
            endTheGame();
        else if (NewGameActivity.StopTheGame >=  ConstantsClass.QuestionsNumberToBeAsked && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()))
        {
            NewGameActivity.StopTheGame=0;
            new AlertDialog.Builder(this)
                    .setTitle("The game is done ")
                    .setMessage("Your current game is finished, please click ok to go to your profile")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), Profile2_ScrollingActivity.class);
                            startActivity(i);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        else {

            if (NewGameActivity.yourTurnToChooseCategory) {
                NewGameActivity.yourTurnToChooseCategory = false;
                Toast.makeText(getApplicationContext(),
                        "It's your turn to pick a category. Just wait a second. ", Toast.LENGTH_LONG)
                        .show();

                final Intent i = new Intent(getApplicationContext(), categoryChoiceActivity.class);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(i);
                    }
                }, 5000);

            } else SetDialogue();
        }
    }
    public void SetDialogue(){

        // new Contact instance has been saved
        new AlertDialog.Builder(this)
                .setTitle("Your result : ")
                .setMessage("Your oponennt is playing his round right now please wait for a notification when he finishes .")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }
    public void endTheGame(){

        // the game is finish go back to your profile save the result in the users table
        if (NewGameActivity.result.getFirstUserResult()>NewGameActivity.result.getSecondtUserResult())
        {

            String firstUserObjectId = NewGameActivity.result.getFirstUSerObjectID();
            System.out.println("The winner is  :    " + firstUserObjectId);


            Backendless.UserService.findById  (firstUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
            public void handleResponse(BackendlessUser backendlessUser )
            {
                System.out.println(backendlessUser.getObjectId());
                int wonGames = (int) backendlessUser.getProperty("WON");
                backendlessUser.setProperty("WON",wonGames +1);

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
                public void handleFault( BackendlessFault fault )
                {
                    System.err.println( "Error - " + fault );
                }});
            // for the loser
            String secondUserObjectId = NewGameActivity.result.getSecondUSerObjectID();
            Backendless.UserService.findById  (secondUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
            public void handleResponse(BackendlessUser backendlessUser )
            {
                //System.out.println(backendlessUser.getObjectId());
                int lostGames = (int) backendlessUser.getProperty("LOST");

                backendlessUser.setProperty("LOST",lostGames +1);

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
                public void handleFault( BackendlessFault fault )
                {
                    System.err.println( "Error - " + fault );
                }});




        }
        else if (NewGameActivity.result.getFirstUserResult()<NewGameActivity.result.getSecondtUserResult()){
            String secondUserObjectId = NewGameActivity.result.getFirstUSerObjectID();
            System.out.println("The winner is  :    " + secondUserObjectId);


            Backendless.UserService.findById  (secondUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
            public void handleResponse(BackendlessUser backendlessUser )
            {
                System.out.println(backendlessUser.getObjectId());
                int wonGames = (int) backendlessUser.getProperty("WON");
                backendlessUser.setProperty("WON",wonGames +1);

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
                public void handleFault( BackendlessFault fault )
                {
                    System.err.println( "Error - " + fault );
                }});


            // for the loser
            String firstUserObjectId = NewGameActivity.result.getFirstUSerObjectID();
            Backendless.UserService.findById  (firstUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
            public void handleResponse(BackendlessUser backendlessUser )
            {
                //System.out.println(backendlessUser.getObjectId());
                int lostGames = (int) backendlessUser.getProperty("LOST");

                backendlessUser.setProperty("LOST",lostGames +1);

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
                public void handleFault( BackendlessFault fault )
                {
                    System.err.println( "Error - " + fault );
                }});


        }
        else if (NewGameActivity.result.getFirstUserResult()==NewGameActivity.result.getSecondtUserResult()){
            String firstUserObjectId = NewGameActivity.result.getFirstUSerObjectID();
            Backendless.UserService.findById  (firstUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
            public void handleResponse(BackendlessUser backendlessUser )
            {
                int drawGames = (int) backendlessUser.getProperty("DRAW");
                backendlessUser.setProperty("DRAW", drawGames+1);

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
                public void handleFault( BackendlessFault fault )
                {
                    System.err.println( "Error - " + fault );
                }});
            String secondUserObjectId = NewGameActivity.result.getSecondUSerObjectID();
            Backendless.UserService.findById  (secondUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
            public void handleResponse(BackendlessUser backendlessUser )
            {
                int drawGames = (int) backendlessUser.getProperty("DRAW");
                backendlessUser.setProperty("DRAW", drawGames+1);

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
                public void handleFault( BackendlessFault fault )
                {
                    System.err.println( "Error - " + fault );
                }});




        }
        new AlertDialog.Builder(this)
                .setTitle("The game is done ")
                .setMessage("Your current game is finished, please click ok to go to your profile")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), Profile2_ScrollingActivity.class);
                        startActivity(i);
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_info)
                .show();


    }
}
