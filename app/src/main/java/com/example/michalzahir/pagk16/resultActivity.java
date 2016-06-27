package com.example.michalzahir.pagk16;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class resultActivity extends AppCompatActivity {
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
        firstUserResultTextView.setText(Integer.toString(intFirstResult)+":" );
        secondUserResultTextView.setText(Integer.toString(intSecondResult));
        if (NewGameActivity.yourTurnToChooseCategory == true ){
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

        }
        else  SetDialogue();
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
}
