package com.example.michalzahir.pagk16;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.SavedGames.SavedGamesDeleting;
import com.example.michalzahir.pagk16.SavedGames.Saved_Games;

import java.util.Random;

/**
 * Created by zahirm on 2016-05-30.
 */
public class gettingQuestions extends Application {
    private static final String TAG = gettingQuestions.class.getSimpleName();
    Context context;
    private Bundle bundle;
    static int QuestionsCounter;
    static public Boolean SavedGame = false;
    public static int[] QuestionsIDs = new int[ConstantsClass.QuestionsNumberToBeAsked];

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }


    public gettingQuestions(Bundle bundle, Context context) {
        this.bundle = bundle;
        this.context = context;
    }

    public static void getQuestions(final Context context) {
        for (QuestionsCounter = 0; QuestionsCounter < ConstantsClass.QuestionsNumberToBeAsked; QuestionsCounter++) {
            Random rn = new Random();
            int ID = rn.nextInt(ConstantsClass.QuestionsQuestSize) + 1;
            System.out.println(ID);
            QuestionsIDs[QuestionsCounter] = ID;
            String whereClause = " ID=" + ID;
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(whereClause);

            Backendless.Persistence.of(QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
                @Override
                public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions) {
                    for (QUESTIONS q : foundQuestions.getData()) {
                        //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                        Backendless.Persistence.of(QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<QUESTIONS>() {
                            @Override
                            public void handleResponse(QUESTIONS response) {
                                // a Contact instance has been found by ObjectId
                                Bundle insideBundle = new Bundle();
                                System.out.println("this is the question from the backendless DB  " + response.getQuestion()
                                        + ".    this is the first answer   " + response.getAnswer_a() + ".   Hurrraaa success !!!!" + response.getCORRECT_A() + " B boolean:" + response.getCORRECT_B() + " D boolean:" + response.getCORRECT_D() + " C boolean:" + response.getCORRECT_C() + "AA" + response.getAnswer_a() + "bA" + response.getANSWER_B() + "cA" + response.getANSWER_C() + "DA" + response.getANSWER_D());
                                insideBundle.putString("Question", response.getQuestion());
                                insideBundle.putString("Answer_A", response.getAnswer_a());
                                insideBundle.putString("Answer_B", response.getANSWER_B());
                                insideBundle.putString("Answer_C", response.getANSWER_C());
                                insideBundle.putString("Answer_D", response.getANSWER_D());
                                insideBundle.putBoolean("correct_A", response.getCORRECT_A());
                                insideBundle.putBoolean("correct_B", response.getCORRECT_B());
                                insideBundle.putBoolean("correct_C", response.getCORRECT_C());
                                insideBundle.putBoolean("correct_D", response.getCORRECT_D());

                                Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                                //StartActivity(bundle ,context);
//                                setBundle(insideBundle);
                                Intent i = new Intent(context, questionActivity.class);
                                i.putExtras(insideBundle);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                            }
                        });
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                }
            });


        }


    }


    public static void getQuestionsForSavedGames(final Context context, Saved_Games savedGame) {


        SavedGame = true;
        String helper = savedGame.getQuestionsIDs().substring(1, savedGame.getQuestionsIDs().length() - 1);
        int tab[] = new int[ConstantsClass.QuestionsNumberToBeAsked];
        String[] strArray = helper.split(",");
        for (int i = 0; i < strArray.length; i++) {
            strArray[i] = strArray[i].replaceAll(" ", "");
            QuestionsIDs[i] = Integer.parseInt(strArray[i]);
        }
        for (int i = savedGame.getQuestionsAnswered(); i < strArray.length; i++) {
            strArray[i] = strArray[i].replaceAll(" ", "");
            tab[i] = Integer.parseInt(strArray[i]);


            //QuestionsIDs[QuestionsCounter] = Integer.parseInt(savedGame.getQuestionsIDs());
            String whereClause = " ID=" + tab[i];
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(whereClause);

            Backendless.Persistence.of(QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
                @Override
                public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions) {
                    for (QUESTIONS q : foundQuestions.getData()) {
                        //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                        Backendless.Persistence.of(QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<QUESTIONS>() {
                            @Override
                            public void handleResponse(QUESTIONS response) {
                                // a Contact instance has been found by ObjectId
                                Bundle insideBundle = new Bundle();
                                System.out.println("this is the question from the backendless DB  " + response.getQuestion()
                                        + ".    this is the first answer   " + response.getAnswer_a() + ".   Hurrraaa success !!!!" + response.getCORRECT_A() + " B boolean:" + response.getCORRECT_B() + " D boolean:" + response.getCORRECT_D() + " C boolean:" + response.getCORRECT_C() + "AA" + response.getAnswer_a() + "bA" + response.getANSWER_B() + "cA" + response.getANSWER_C() + "DA" + response.getANSWER_D());
                                insideBundle.putString("Question", response.getQuestion());
                                insideBundle.putString("Answer_A", response.getAnswer_a());
                                insideBundle.putString("Answer_B", response.getANSWER_B());
                                insideBundle.putString("Answer_C", response.getANSWER_C());
                                insideBundle.putString("Answer_D", response.getANSWER_D());
                                insideBundle.putBoolean("correct_A", response.getCORRECT_A());
                                insideBundle.putBoolean("correct_B", response.getCORRECT_B());
                                insideBundle.putBoolean("correct_C", response.getCORRECT_C());
                                insideBundle.putBoolean("correct_D", response.getCORRECT_D());

                                Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                                //StartActivity(bundle ,context);
//                                setBundle(insideBundle);
                                Intent i = new Intent(context, questionActivity.class);
                                i.putExtras(insideBundle);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                            }
                        });
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                }
            });


        }
        SavedGamesDeleting.DeleteSavedGame(savedGame);

    }


}
