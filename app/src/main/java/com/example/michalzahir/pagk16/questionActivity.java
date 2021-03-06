package com.example.michalzahir.pagk16;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.Helper.AutoResizeTextView;
import com.example.michalzahir.pagk16.SavedGames.GamesSaving;
import com.example.michalzahir.pagk16.ServiceAppOff.MyService;
import com.facebook.appevents.AppEventsLogger;

import java.util.Timer;
import java.util.TimerTask;

public class questionActivity extends AppCompatActivity {
    private com.example.michalzahir.pagk16.Helper.AutoResizeTextView QuestionTV;
    private Button AnswerAButton;
    private Button AnswerBButton;
    private Button AnswerCButton;
    private Button AnswerDButton;
    Boolean AnswerABoolean;
    Boolean AnswerBBoolean;
    Boolean AnswerCBoolean;
    Boolean AnswerDBoolean;
    Bundle bundle;
    public int AnsweredQuestion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MyService.class));

        setContentView(R.layout.activity_question);
        QuestionTV = (com.example.michalzahir.pagk16.Helper.AutoResizeTextView) findViewById(R.id.QuestionTextView);
        AnswerAButton = (Button) findViewById(R.id.AnswerButtonA);
        AnswerBButton = (Button) findViewById(R.id.AnswerButtonB);
        AnswerCButton = (Button) findViewById(R.id.AnswerButtonC);
        AnswerDButton = (Button) findViewById(R.id.AnswerButtonD);
         bundle = this.getIntent().getExtras();

        System.out.println("The Question bundle  " + "QuestionID =" +bundle.getInt("QuestionID") +bundle.getString("Question") + bundle.getString("Answer_A") + bundle.getString("Answer_B") + bundle.getString("Answer_C") + bundle.getString("Answer_D"));
        QuestionTV.setText(bundle.getString("Question"));
        QuestionTV.resizeText();
        AnswerAButton.setText(bundle.getString("Answer_A"));
        AnswerBButton.setText(bundle.getString("Answer_B"));
        AnswerCButton.setText(bundle.getString("Answer_C"));
        AnswerDButton.setText(bundle.getString("Answer_D"));
        AnswerABoolean = bundle.getBoolean("correct_A");
        AnswerBBoolean = bundle.getBoolean("correct_B");
        AnswerCBoolean = bundle.getBoolean("correct_C");
        AnswerDBoolean = bundle.getBoolean("correct_D");

        if (bundle.containsKey("FB_game")){
            NewGameActivity.AddUserToQueue = bundle.getBoolean("AddUserToQueue");
            fbFriendsListActivity.FbGame =  bundle.getBoolean("FB_game");
        }
        if (bundle.containsKey("firstUSerObjectID")) {

            NewGameActivity.result = new gameResult(Integer.parseInt(bundle.getString("firstUserResult")), Integer.parseInt(bundle.getString("secondtUserResult")), bundle.getString("firstUSerObjectID"), bundle.getString("secondUSerObjectID"));
            NewGameActivity.result.setFirstUserResult(Integer.parseInt(bundle.getString("firstUserResult")));
            NewGameActivity.result.setSecondtUserResult(Integer.parseInt(bundle.getString("secondtUserResult")));
            NewGameActivity.result.setFirstUSerObjectID(bundle.getString("firstUSerObjectID"));
            NewGameActivity.result.setSecondUSerObjectID(bundle.getString("secondUSerObjectID"));
            NewGameActivity.yourTurnToChooseCategory = true;
            pushNotification.GetOpponentUserObjID(getApplicationContext());


        }

        AnswerAButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                IncreaseQuesiotnsAnswered() ;
                DisableButtonsAfterClick();

                if (AnswerABoolean == true) {


                    AnswerAButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));


                    incrementResultForGoodAnswer();

                } else {
                    AnswerAButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }

            }
        });

        AnswerBButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                IncreaseQuesiotnsAnswered() ;
                DisableButtonsAfterClick();
                if (AnswerBBoolean == true) {

                    AnswerBButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                } else {
                    AnswerBButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }

            }
        });
        AnswerCButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                IncreaseQuesiotnsAnswered() ;
                DisableButtonsAfterClick();
                if (AnswerCBoolean == true) {

                    AnswerCButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                } else {
                    AnswerCButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
//                    SystemClock.sleep(4000);

                    findTHeRightAnswer();
                }

            }
        });


        AnswerDButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                IncreaseQuesiotnsAnswered() ;
                DisableButtonsAfterClick();
                if (AnswerDBoolean == true) {

                    AnswerDButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//                    SystemClock.sleep(4000);
                    incrementResultForGoodAnswer();


                } else {
                    AnswerDButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public void incrementResultForGoodAnswer() {

        Log.d("Bug fb user object id", " Logging the error where the app is off :  player object ID  " + playerObejtID.getUserObjectID() +" first user object ID  " +NewGameActivity.result.getFirstUSerObjectID() + "  second user object ID"+NewGameActivity.result.getSecondUSerObjectID() + "  1st user result"+NewGameActivity.result.getFirstUserResult() +"  scnd user result"+NewGameActivity.result.getSecondtUserResult() +
                " bFriendsListActivity.result.getFirstUSerObjectID()");

        if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID())) {
            NewGameActivity.result.Increment1stUserResult();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    NewGameActivity.result.publishResults(questionActivity.this, bundle);
                }
            }, 3000);
        }
        else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID())) {
            NewGameActivity.result.Increment2ndUserResult();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    NewGameActivity.result.publishResults(questionActivity.this, bundle);
                }
            }, 3000);
        }

        else if (playerObejtID.getUserObjectID().equals(fbFriendsListActivity.result.getFirstUSerObjectID())){
            fbFriendsListActivity.result.Increment1stUserResult();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    fbFriendsListActivity.result.publishResults(questionActivity.this, bundle);
                }
            }, 3000);
        }
        else if(playerObejtID.getUserObjectID().equals(fbFriendsListActivity.result.getSecondUSerObjectID())){
            fbFriendsListActivity.result.Increment2ndUserResult();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    fbFriendsListActivity.result.publishResults(questionActivity.this, bundle);
                }
            }, 3000);

        }
    }

    public void findTHeRightAnswer() {
        if (AnswerABoolean == true)
            AnswerAButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerBBoolean == true)
            AnswerBButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerCBoolean == true)
            AnswerCButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerDBoolean == true)
            AnswerDButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//        SystemClock.sleep(4000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                NewGameActivity.result.publishResults(questionActivity.this, bundle);
            }
        }, 3000);

    }
//    @Override
//    public void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//       // com.example.michalzahir.pagk16.SavedGames.GamesSaving.SaveGame(this.getClass().getSimpleName());
//        Log.e("onDetachedFromWindow", "activity dying");
//    }
//    @Override
//    protected void onPause(){
//
//        super.onPause();
//        if (this.isFinishing ())
//        {
//            //com.example.michalzahir.pagk16.SavedGames.GamesSaving.SaveGame(this.getClass().getSimpleName());
//
//        }
//        else
//        {
//            // activity not dying just stopping
//        }
//    }
//    @Override
//    protected void onStop() {
//
//
//
//
//        super.onStop();
//       // com.example.michalzahir.pagk16.SavedGames.GamesSaving.SaveGame(this.getClass().getSimpleName());
//
//
//
//        //playerObejtID.SaveUserObjectIDOnDestroy(getApplicationContext());
//        //com.example.michalzahir.pagk16.SavedGames.GamesSaving.SaveGame(this.getClass().getSimpleName());
//        // Logs 'app deactivate' App Event.
//        AppEventsLogger.deactivateApp(this);
//    }
//    @Override
//    protected void onDestroy() {
//        //com.example.michalzahir.pagk16.SavedGames.GamesSaving.SaveGame(this.getClass().getSimpleName());
//
//        super.onDestroy();
//
//        //playerObejtID.SaveUserObjectIDOnDestroy(getApplicationContext());
//        // Logs 'app deactivate' App Event.
//        AppEventsLogger.deactivateApp(this);
//    }
    void DisableButtonsAfterClick(){
        AnswerAButton.setEnabled(false);
        AnswerAButton.setClickable(false);

        AnswerBButton.setEnabled(false);
        AnswerBButton.setClickable(false);

        AnswerCButton.setEnabled(false);
        AnswerCButton.setClickable(false);

        AnswerDButton.setEnabled(false);
        AnswerDButton.setClickable(false);


    }
    static void IncreaseQuesiotnsAnswered(){

        GamesSaving.QuestionsAnswered +=1;
    }
}
