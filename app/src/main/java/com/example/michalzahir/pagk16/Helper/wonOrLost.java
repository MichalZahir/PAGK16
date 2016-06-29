package com.example.michalzahir.pagk16.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.playerObejtID;

/**
 * Created by zahirm on 2016-06-29.
 */
public class wonOrLost  {
    static public void CheckWhoWon(final Context c){
        if(playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()) && NewGameActivity.result.getFirstUserResult()> NewGameActivity.result.getSecondtUserResult())
        {
            new AlertDialog.Builder(c)
                    .setTitle("Congrats! You just won. ")
                    .setMessage("Please click OK to go to Your profile")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(c, Profile2_ScrollingActivity.class);
                            c.startActivity(i);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        else if(playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()) && NewGameActivity.result.getSecondtUserResult()> NewGameActivity.result.getFirstUserResult() )
        {
            new AlertDialog.Builder(c)
                    .setTitle("Congrats! You just won. ")
                    .setMessage("Please click OK to go to Your profile")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(c, Profile2_ScrollingActivity.class);
                            c.startActivity(i);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        else if (NewGameActivity.result.getSecondtUserResult()==NewGameActivity.result.getFirstUserResult()){

            new AlertDialog.Builder(c)
                    .setTitle("ouups we Have a draw!")
                    .setMessage("Please click OK to go to Your profile")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(c, Profile2_ScrollingActivity.class);
                            c.startActivity(i);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();

        }

        // the losin part
        else if(playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()) && NewGameActivity.result.getSecondtUserResult()< NewGameActivity.result.getFirstUserResult() )
        {
            new AlertDialog.Builder(c)
                    .setTitle("Hard luck, You lost ")
                    .setMessage("Try to play again, Please click OK to go to Your profile")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(c, Profile2_ScrollingActivity.class);
                            c.startActivity(i);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
        else if(playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()) && NewGameActivity.result.getSecondtUserResult()> NewGameActivity.result.getFirstUserResult() )
        {
            new AlertDialog.Builder(c)
                    .setTitle("Hard luck, You lost ")
                    .setMessage("Try to play again, Please click OK to go to Your profile")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(c, Profile2_ScrollingActivity.class);
                            c.startActivity(i);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }

    }
}
