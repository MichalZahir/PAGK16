package com.example.michalzahir.pagk16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class questionActivity extends AppCompatActivity {
    private TextView QuestionTV;
    private Button AnswerAButton;
    private Button AnswerBButton;
    private Button AnswerCButton;
    private Button AnswerDButton;
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


    }
}
