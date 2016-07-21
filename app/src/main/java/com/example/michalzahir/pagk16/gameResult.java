package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.model.User;

import java.util.Arrays;

/**
 * Created by zahirm on 2016-06-02.
 */
public class gameResult {


    int firstUserResult;
    int secondtUserResult;
    String firstUSerObjectID;
    String secondUSerObjectID;
    static int questionsAnswered;

    public gameResult(int firstUserResult, int secondtUserResult, String firstUSerObjectID, String secondUSerObjectID) {
        this.firstUserResult = firstUserResult;
        this.secondtUserResult = secondtUserResult;
        this.firstUSerObjectID = firstUSerObjectID;
        this.secondUSerObjectID = secondUSerObjectID;
    }

    public gameResult() {
    }

    public int getSecondtUserResult() {
        return secondtUserResult;
    }

    public void setSecondtUserResult(int secondtUserResult) {
        this.secondtUserResult = secondtUserResult;
    }

    public int getFirstUserResult() {
        return firstUserResult;
    }

    public void setFirstUserResult(int firstUserResult) {
        this.firstUserResult = firstUserResult;
    }

    public String getFirstUSerObjectID() {
        return firstUSerObjectID;
    }

    public void setFirstUSerObjectID(String firstUSerObjectID) {
        this.firstUSerObjectID = firstUSerObjectID;
    }

    public String getSecondUSerObjectID() {
        return secondUSerObjectID;
    }

    public void setSecondUSerObjectID(String secondUSerObjectID) {
        this.secondUSerObjectID = secondUSerObjectID;
    }

    public void Increment1stUserResult() {

        this.firstUserResult = this.firstUserResult + 1;
    }

    public void Increment2ndUserResult() {

        this.secondtUserResult = this.secondtUserResult + 1;
    }

    public void publishResults(final Context context, Bundle bundle) {
        Bundle resultsBundle = new Bundle();
        MainActivity.user = User.getInstance();
        MainActivity.user.setResult(getFirstUserResult());
        resultsBundle.putInt("1st user result", getFirstUserResult());
        resultsBundle.putInt("2nd user result", getSecondtUserResult());
//        final Intent i = new Intent(context, resultActivity.class);
//        i.putExtras(resultsBundle);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                context.startActivity(i);
//            }
//        }, 5000);
//        i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
        ((questionActivity) context).finish();
        questionsAnswered++;

        Log.d(" queue null object", "AddUserToQueue  :" + NewGameActivity.AddUserToQueue + "      NewGameActivity.StopTheGame  " + NewGameActivity.StopTheGame);
        Log.d(" FbGaame", "   :  " + fbFriendsListActivity.FbGame);
        Log.d("Question Counter", String.valueOf(gettingQuestions.QuestionsCounter));
        if (fbFriendsListActivity.FbGame){
            if (questionsAnswered >= ConstantsClass.QuestionsNumberToBeAsked && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID())) {
                bundle.putString("QuestionIDS", Arrays.toString(gettingQuestions.QuestionsIDs));
                pushNotification.PublishNotification(context,bundle);
                final Intent i = new Intent(context, resultActivity.class);
                i.putExtras(resultsBundle);
                context.startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
            else if(questionsAnswered >= ConstantsClass.QuestionsNumberToBeAsked && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()))
            {
                pushNotification.PublishTheLastResultNotificaton(context, bundle);


            }
            }
        else {


        if (questionsAnswered >= ConstantsClass.QuestionsNumberToBeAsked) {

            final Intent i = new Intent(context, resultActivity.class);
            i.putExtras(resultsBundle);
            context.startActivity(i);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);


            if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()))
                pushNotification.PublishTheLastResultNotificaton(context, bundle);
            if (NewGameActivity.AddUserToQueue)
                com.example.michalzahir.pagk16.Helper.user_Queue_Updater.saveNewPlayer();


        }
        }


        //        if (fbFriendsListActivity.FbGame) {
        //            if (NewGameActivity.StopTheGame != 0 && NewGameActivity.StopTheGame < ConstantsClass.QuestionsNumberToBeAsked)
        //                pushNotification.PublishNotification(context, bundle);
        //            else if (NewGameActivity.StopTheGame == 0 && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()))
        //                pushNotification.PublishNotification(context, bundle);
        //        } else {
        //            if (NewGameActivity.AddUserToQueue && NewGameActivity.StopTheGame == 0)
        //                com.example.michalzahir.pagk16.Helper.user_Queue_Updater.saveNewPlayer();
        //
        //            //context.startActivity(i);
        //            if (NewGameActivity.StopTheGame != 0 && NewGameActivity.StopTheGame < ConstantsClass.QuestionsNumberToBeAsked) {
        //
        //                pushNotification.PublishNotification(context, bundle);
        //            }
        //        }
    }
}
