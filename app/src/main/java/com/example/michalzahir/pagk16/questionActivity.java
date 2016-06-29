package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class questionActivity extends AppCompatActivity {
    private TextView QuestionTV;
    private Button AnswerAButton;
    private Button AnswerBButton;
    private Button AnswerCButton;
    private Button AnswerDButton;
    Boolean AnswerABoolean;
    Boolean AnswerBBoolean;
    Boolean AnswerCBoolean;
    Boolean AnswerDBoolean;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);
        QuestionTV = (TextView) findViewById(R.id.QuestionTextView);
        AnswerAButton = (Button) findViewById(R.id.AnswerButtonA);
        AnswerBButton = (Button) findViewById(R.id.AnswerButtonB);
        AnswerCButton= (Button) findViewById(R.id.AnswerButtonC);
        AnswerDButton = (Button) findViewById(R.id.AnswerButtonD);
         bundle = this.getIntent().getExtras();

        System.out.println("The Question bundle  "+ bundle.getString("Question")+bundle.getString("Answer_A")+bundle.getString("Answer_B")+bundle.getString("Answer_C")+bundle.getString("Answer_D"));
        QuestionTV.setText(bundle.getString("Question"));
        AnswerAButton.setText(bundle.getString("Answer_A"));
        AnswerBButton.setText(bundle.getString("Answer_B"));
        AnswerCButton.setText(bundle.getString("Answer_C"));
        AnswerDButton.setText(bundle.getString("Answer_D"));
        AnswerABoolean = bundle.getBoolean("correct_A");
        AnswerBBoolean = bundle.getBoolean("correct_B");
        AnswerCBoolean = bundle.getBoolean("correct_C");
        AnswerDBoolean = bundle.getBoolean("correct_D");
        if(bundle.containsKey("firstUSerObjectID")){

            NewGameActivity.result = new gameResult(Integer.parseInt(bundle.getString("firstUserResult")),Integer.parseInt(bundle.getString("secondtUserResult")),bundle.getString("firstUSerObjectID"),bundle.getString("secondUSerObjectID"));
            NewGameActivity.result.setFirstUserResult(Integer.parseInt(bundle.getString("firstUserResult")));
            NewGameActivity.result.setSecondtUserResult(Integer.parseInt(bundle.getString("secondtUserResult")));
            NewGameActivity.result.setFirstUSerObjectID(bundle.getString("firstUSerObjectID"));
            NewGameActivity.result.setSecondUSerObjectID(bundle.getString("secondUSerObjectID"));
            NewGameActivity.yourTurnToChooseCategory = true;
        }

        AnswerAButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),
//                        resultActivity.class);
//                startActivity(i);
                if (AnswerABoolean==true){


                AnswerAButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                }
                else{
                    AnswerAButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }

        }});

        AnswerBButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AnswerBBoolean==true){

                    AnswerBButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                }
                else{
                    AnswerBButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }

            }});
        AnswerCButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AnswerCBoolean==true){

                    AnswerCButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                }
                else{
                    AnswerCButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
//                    SystemClock.sleep(4000);

                    findTHeRightAnswer();
                }

            }});


        AnswerDButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AnswerDBoolean==true){

                    AnswerDButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//                    SystemClock.sleep(4000);
                    incrementResultForGoodAnswer();


                }
                else{
                    AnswerDButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }

            }});

    }


    public void incrementResultForGoodAnswer(){
        // TODO: 2016-06-27 Bug, users logged in with fb got no userobject ID wich leads to a null pointer exception in this palce.
        if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID())) {
            NewGameActivity.result.Increment1stUserResult();
            NewGameActivity.result.publishResults(this,bundle);
        }
        if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID())) {
            NewGameActivity.result.Increment2ndUserResult();
            NewGameActivity.result.publishResults(this,bundle);
        }

    }

    public void findTHeRightAnswer(){
        if (AnswerABoolean==true)AnswerAButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerBBoolean==true)AnswerBButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerCBoolean==true)AnswerCButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerDBoolean==true)AnswerDButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
//        SystemClock.sleep(4000);

        NewGameActivity.result.publishResults(this, bundle);

    }





}
