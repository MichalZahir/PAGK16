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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);
        QuestionTV = (TextView) findViewById(R.id.QuestionTextView);
        AnswerAButton = (Button) findViewById(R.id.AnswerButtonA);
        AnswerBButton = (Button) findViewById(R.id.AnswerButtonB);
        AnswerCButton= (Button) findViewById(R.id.AnswerButtonC);
        AnswerDButton = (Button) findViewById(R.id.AnswerButtonD);
        Bundle bundle = this.getIntent().getExtras();
        System.out.println(bundle.getString("Question")+bundle.getString("Answer_A")+bundle.getString("Answer_B")+bundle.getString("Answer_C")+bundle.getString("Answer_D"));
        QuestionTV.setText(bundle.getString("Question"));
        AnswerAButton.setText(bundle.getString("Answer_A"));
        AnswerBButton.setText(bundle.getString("Answer_B"));
        AnswerCButton.setText(bundle.getString("Answer_C"));
        AnswerDButton.setText(bundle.getString("Answer_D"));
        AnswerABoolean = bundle.getBoolean("correct_A");
        AnswerBBoolean = bundle.getBoolean("correct_B");
        AnswerCBoolean = bundle.getBoolean("correct_C");
        AnswerDBoolean = bundle.getBoolean("correct_D");
        AnswerAButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),
//                        resultActivity.class);
//                startActivity(i);
                if (AnswerABoolean==true){


                AnswerAButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                }
                else{
                    AnswerAButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }
                // TODO: 2016-06-02 Send Notfication and Saved Questions to the other opponent
        }});

        AnswerBButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AnswerBBoolean==true){

                    AnswerBButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                }
                else{
                    AnswerBButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }
                // TODO: 2016-06-02 Send Notfication and Saved Questions to the other opponent
            }});
        AnswerCButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AnswerCBoolean==true){

                    AnswerCButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
                    SystemClock.sleep(4000);

                    incrementResultForGoodAnswer();

                }
                else{
                    AnswerCButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    SystemClock.sleep(4000);

                    findTHeRightAnswer();
                }
                // TODO: 2016-06-02 Send Notfication and Saved Questions to the other opponent
            }});


        AnswerDButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AnswerDBoolean==true){

                    AnswerDButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
                    SystemClock.sleep(4000);
                    incrementResultForGoodAnswer();


                }
                else{
                    AnswerDButton.setBackgroundColor(getResources().getColor(R.color.badAnswer));
                    findTHeRightAnswer();
                }
                // TODO: 2016-06-02 Send Notfication and Saved Questions to the other opponent
            }});

    }


    public void incrementResultForGoodAnswer(){

        if (playerObejtID.getUserObjectID()==NewGameActivity.result.getFirstUSerObjectID()) {NewGameActivity.result.Increment1stUserResult();
            NewGameActivity.result.publishResults(this);
        }
        if (playerObejtID.getUserObjectID()==NewGameActivity.result.getSecondUSerObjectID()) {NewGameActivity.result.Increment2ndUserResult();
            NewGameActivity.result.publishResults(this);
        }

    }

    public void findTHeRightAnswer(){
        if (AnswerABoolean==true)AnswerAButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerBBoolean==true)AnswerBButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerCBoolean==true)AnswerCButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        if (AnswerDBoolean==true)AnswerDButton.setBackgroundColor(getResources().getColor(R.color.goodAnswer));
        SystemClock.sleep(4000);

        NewGameActivity.result.publishResults(this);

    }





}
