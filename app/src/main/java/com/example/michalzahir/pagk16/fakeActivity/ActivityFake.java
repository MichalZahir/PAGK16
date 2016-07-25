package com.example.michalzahir.pagk16.fakeActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.michalzahir.pagk16.R;

public class ActivityFake extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_fake);
        Bundle bundle = this.getIntent().getExtras();
        //finish();

        com.example.michalzahir.pagk16.Helper.UserQueueQuestionRetriever.RetrieveQuestionForFirstRound(bundle.getString("QuestionIDS"), this);
        //


    }
//    @Override
//    protected void onPause(){
//        super.onPause();
//    }
}
