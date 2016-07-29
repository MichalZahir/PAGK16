package com.example.michalzahir.pagk16.SavedGames;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.Messaging;
import com.backendless.exceptions.BackendlessException;
import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.gettingQuestions;
import com.example.michalzahir.pagk16.playerObejtID;
import com.example.michalzahir.pagk16.pushNotification;
import com.example.michalzahir.pagk16.questionActivity;

import java.util.Arrays;

/**
 * Created by zahirm on 2016-07-18.
 */
public class GamesSaving {
    private static final String TAG = " GameSaving";
    public static int QuestionsAnswered  ;


    public static void SaveGame(String activity) {
        final Saved_Games saved_games = new Saved_Games();
        saved_games.setFirstUserResult(NewGameActivity.result.getFirstUserResult());
        saved_games.setActivity(activity);
        saved_games.setFbGame(fbFriendsListActivity.FbGame);
        saved_games.setFirstUserID(NewGameActivity.result.getFirstUSerObjectID());
        saved_games.setSecondUserID(NewGameActivity.result.getSecondUSerObjectID());
        setDevicesIDS(saved_games);
        SetWhosTurn(saved_games);
        setOpponentDeviceID(saved_games);
        saved_games.setQuestionsAnswered( QuestionsAnswered);
        Log.d(TAG, "The Questions Answered : " +  QuestionsAnswered);
        saved_games.setSecondUserName("asd");
        saved_games.setQuestionsIDs(Arrays.toString(gettingQuestions.QuestionsIDs));
        saved_games.setSecondUserResult(NewGameActivity.result.getSecondtUserResult());
        saved_games.setStopTheGame(NewGameActivity.StopTheGame);
        final Saved_Games[] response = {null};


        // save object asynchronously
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    response[0] = Backendless.Persistence.save(saved_games);
                } catch (BackendlessException fault) {
                    Log.d(TAG, "Saving the game in the ongoing game table has failed :" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                }
                Log.d(TAG, "The Saved Games has been saved successfully " + response[0].getObjectId());

            }});

        t.start(); // spawn thread
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Backendless.Persistence.save(saved_games, new AsyncCallback<Saved_Games>() {
//            public void handleResponse(Saved_Games response) {
//
//                Log.d(TAG, "The Saved Games has been saved successfully " + response.getObjectId());
//            }
//
//            public void handleFault(BackendlessFault fault) {
//
//                Log.d(TAG, "Saving the game in the ongoing game table has failed :" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
//
//            }
//        });

    }

    public static void SetWhosTurn(Saved_Games saved_games) {
        String WhosTurn = null;
        if ( NewGameActivity.result.getFirstUSerObjectID().equals(playerObejtID.getUserObjectID()))
            WhosTurn = "1st user";
        else if (  NewGameActivity.result.getSecondUSerObjectID().equals(playerObejtID.getUserObjectID()))
            WhosTurn = "2nd user";


        saved_games.setWhosTurn(WhosTurn);
    }
    public static  void setDevicesIDS(Saved_Games saved_games){
        if (NewGameActivity.result.getFirstUSerObjectID().equals(playerObejtID.getUserObjectID()))
        saved_games.setFirstUserDeviceID(Messaging.DEVICE_ID);
        else if (NewGameActivity.result.getSecondUSerObjectID().equals(playerObejtID.getUserObjectID()))
        saved_games.setSecondUserDeviceID(Messaging.DEVICE_ID);
    }
    public static void setOpponentDeviceID(Saved_Games saved_games){
        if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID()))
         saved_games.setSecondUserDeviceID(pushNotification.OpponentDeviceID);
        else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID()))
            saved_games.setFirstUserDeviceID(pushNotification.OpponentDeviceID);

    }
}
