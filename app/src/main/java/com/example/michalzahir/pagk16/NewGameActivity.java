package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;

public class NewGameActivity extends AppCompatActivity {
    private Button newFBGameButton;
    private Button newRandomGameButton;
    static gameResult result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        newFBGameButton = (Button) findViewById(R.id.fbNewGameButton);
        newRandomGameButton = (Button) findViewById(R.id.RandomNewGameButton);
        result = new gameResult();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

                playersQueue PLAYERSQUEUE = playersQueue.getPlayersQueueInstance();
                if(PLAYERSQUEUE.isEmpty()){

                    playersQueue.AddUserToPlayersQueue(playerObejtID.getUserObjectID());
                    result.setFirstUSerObjectID(playerObejtID.getUserObjectID());
                    result.setFirstUserResult(0);

                }
                else {
                    result.setSecondUSerObjectID(playerObejtID.getUserObjectID());
                    result.setSecondtUserResult(0);
                    playersQueue.RemoveUserFromPlayersQueue();
                    SavedQuestions.ListToBundleStartQueAct(getApplicationContext());



                }


                Intent i = new Intent(getApplicationContext(),
                        categoryChoiceActivity.class);
                startActivity(i);
                //finish();


            }
        });


        newFBGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                pushNotification.PublishNotification(getApplicationContext());

            }
        });
        }


    }
