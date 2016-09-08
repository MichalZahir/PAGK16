package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.SavedGames.GamesSaving;
import com.example.michalzahir.pagk16.model.User;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Arrays;

/**
 * Created by zahirm on 2016-06-02.
 */
public class gameResult {


    int firstUserResult;
    int secondtUserResult;
    String firstUSerObjectID;
    String secondUSerObjectID;
    public static int questionsAnswered;
    public static String AnsweredQuestionsIDS;


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
        AnsweredQuestionsIDS = AnsweredQuestionsIDS + "," + String.valueOf(bundle.getInt("QuestionID"));


        ((questionActivity) context).finish();
        questionsAnswered++;

        Log.d(" queue null object", "AddUserToQueue  :" + NewGameActivity.AddUserToQueue + "      NewGameActivity.StopTheGame  " + NewGameActivity.StopTheGame);
        Log.d(" FbGaame", "   :  " + fbFriendsListActivity.FbGame);
        Log.d("Question Counter", String.valueOf(gettingQuestions.QuestionsCounter));
        if (fbFriendsListActivity.FbGame) {
            if (questionsAnswered >= ConstantsClass.QuestionsNumberToBeAsked && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID())) {
                bundle.putString("QuestionIDS", Arrays.toString(gettingQuestions.QuestionsIDs));
                GamesSaving.QuestionsAnswered = 0;
                pushNotification.PublishNotification(context, bundle);
                final Intent i = new Intent(context, resultActivity.class);
                i.putExtras(resultsBundle);


                context.startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            } else if (questionsAnswered >= ConstantsClass.QuestionsNumberToBeAsked && playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID())) {
                GamesSaving.QuestionsAnswered = 0;
                pushNotification.PublishTheLastResultNotificaton(context, bundle);
                final Intent i = new Intent(context, resultActivity.class);
                i.putExtras(resultsBundle);

                context.startActivity(i);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


            }
        } else {

            Log.d("Random Game result ", "questionsAnswered " + questionsAnswered + " SecondUSerObjectID" + NewGameActivity.result.getSecondUSerObjectID() + " 1st user obj id" + NewGameActivity.result.getFirstUSerObjectID());
            if (questionsAnswered >= ConstantsClass.QuestionsNumberToBeAsked) {
                if (RankingActivity.RankingGame) {
                    fbFriendsListActivity.FbGame = false;

                    bundle.putString("QuestionIDS", Arrays.toString(gettingQuestions.QuestionsIDs));
                    GamesSaving.QuestionsAnswered = 0;
                    pushNotification.PublishNotification(context, bundle);
                    final Intent i = new Intent(context, resultActivity.class);
                    i.putExtras(resultsBundle);

                    context.startActivity(i);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                } else {


                    GamesSaving.QuestionsAnswered = 0;
                    Intent i = new Intent(context, resultActivity.class);
                    i.putExtras(resultsBundle);
                    Log.d("b4 starting Context =  ", context.toString());
                    Log.d("b4 starting", " the result activity");

                    context.startActivity(i);
                    Log.d("The activity", i.toString());
                    Log.d("after starting", " the result activity");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("after adding the flag", " the result activity");

                    context.startActivity(i);
                    Log.d("after starting the ", "  activity for the second time");

                    if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()))
                        pushNotification.PublishTheLastResultNotificaton(context, bundle);
                    if (NewGameActivity.AddUserToQueue)
                        com.example.michalzahir.pagk16.Helper.user_Queue_Updater.saveNewPlayer();


                }
            }
        }


    }


}
