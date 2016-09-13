package com.example.michalzahir.pagk16.Helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.ASTRONOMY_QUESTIONS;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.CHEMISTRY_QUESTIONS;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.GEOGRAPHY_QUESTIONS;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.HISTORY_QUESTIONS;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.LITERATURE_QUESTIONS;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.SPORT_QUESTIONS;
import com.example.michalzahir.pagk16.ConstantsClass;
import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.NewGameActivity;
import com.example.michalzahir.pagk16.QUESTIONS;
import com.example.michalzahir.pagk16.gettingQuestions;
import com.example.michalzahir.pagk16.questionActivity;

import java.util.Random;

/**
 * Created by zahirm on 2016-07-05.
 */
public class UserQueueQuestionRetriever {
    public static final String TAG = "Ques Retrieve 1 round";

    public static void RetrieveQuestionForFirstRound(String QuestionIDArray,   final Context context){
        com.example.michalzahir.pagk16.SavingMyAnsweredQuestions.QuestionsIDs.AnsweredQuesIDs = QuestionIDArray;

        String helper = QuestionIDArray.substring(1,QuestionIDArray.length()-1);
            int tab [] = new int[ConstantsClass.QuestionsNumberToBeAsked];
            String[] strArray = helper.split(",");

                 for(int i = 0; i < strArray.length; i++) {
                     strArray[i]= strArray[i].replaceAll(" ","");
                     tab[i] = Integer.parseInt(strArray[i]);
                     gettingQuestions.QuestionsIDs[i] = tab[i] ;
                     Log.d(TAG, "The ids from the question passed with the bundle for the first round " + tab [i]);
            String whereClause = " ID=" + tab[i];
                     BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(whereClause);


            Backendless.Persistence.of(QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
                @Override
                public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions) {
                    for (QUESTIONS q : foundQuestions.getData()) {

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
//                                insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
//                                insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
//                                insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
//                                insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());

                                Log.d(TAG, " " + insideBundle);
                                //StartActivity(bundle ,context);
                                System.out.println("bundle from RetrieveQuestionForFirstRound : " + insideBundle);
                                Intent i = new Intent(context, questionActivity.class);
                                i.putExtras(insideBundle);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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
    public static void RetrievefbQuestionForFirstRound(String QuestionIDArray,   final Context context){

        String helper = QuestionIDArray.substring(1,QuestionIDArray.length()-1);
        int tab [] = new int[ConstantsClass.QuestionsNumberToBeAsked];
        String[] strArray = helper.split(",");

        for(int i = 0; i < strArray.length; i++) {
            strArray[i]= strArray[i].replaceAll(" ","");
            tab[i] = Integer.parseInt(strArray[i]);
            gettingQuestions.QuestionsIDs[i] = tab[i] ;
            Log.d(TAG, "The ids from the question passed with the bundle for the first round " + tab [i]);
            String whereClause = " ID=" + tab[i];
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(whereClause);


            BackendlessCollection<QUESTIONS> foundQuestions = null;
            try {
                foundQuestions = Backendless.Persistence.of(QUESTIONS.class).find(dataQuery);
            } catch (BackendlessException fault) {
                Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
            }
            assert foundQuestions != null;
            for (QUESTIONS q : foundQuestions.getData()) {
                QUESTIONS response = null;
                try {
                    response = Backendless.Persistence.of(QUESTIONS.class).findById(q.getObjectId());
                } catch (BackendlessException fault) {
                    Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                }
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
//                                insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
//                                insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
//                                insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
//                                insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());

                Log.d(TAG, " " + insideBundle);
                //StartActivity(bundle ,context);
                System.out.println("bundle from the middle tier : " + insideBundle);
                Intent in = new Intent(context, questionActivity.class);
                in.putExtras(insideBundle);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(in);

            }



    }}
    public static void get_sport_questions(int QuestionID, final Context context) {


        String whereClause = " ID=" + QuestionID;
         BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of(SPORT_QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<SPORT_QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<SPORT_QUESTIONS> foundQuestions) {
                for (SPORT_QUESTIONS q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(SPORT_QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<SPORT_QUESTIONS>() {
                        @Override
                        public void handleResponse(SPORT_QUESTIONS response) {
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
                            insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
                            insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
                            insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
                            insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());






                            Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                            //StartActivity(bundle ,context);
                            System.out.println("bundle from the middle tier : " + insideBundle);
                            Intent i = new Intent(context, questionActivity.class);
                            i.putExtras(insideBundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public static void get_history_questions(int QuestionID, final Context context) {
        Random rn = new Random();
        int ID = rn.nextInt(ConstantsClass.HistoryQuestiSize) + 1;
        System.out.println(ID);
        String whereClause = " ID=" + QuestionID;
         BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of(HISTORY_QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<HISTORY_QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<HISTORY_QUESTIONS> foundQuestions) {
                for (HISTORY_QUESTIONS q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(HISTORY_QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<HISTORY_QUESTIONS>() {
                        @Override
                        public void handleResponse(HISTORY_QUESTIONS response) {
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
                            insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
                            insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
                            insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
                            insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());

                            Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                            //StartActivity(bundle ,context);
                            System.out.println("bundle from the middle tier : " + insideBundle);
                            Intent i = new Intent(context, questionActivity.class);
                            i.putExtras(insideBundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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

    public static void get_chemistry_questions(int QuestionID, final Context context) {

        String whereClause = " ID=" + QuestionID;
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of(CHEMISTRY_QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<CHEMISTRY_QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<CHEMISTRY_QUESTIONS> foundQuestions) {
                for (CHEMISTRY_QUESTIONS q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(CHEMISTRY_QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<CHEMISTRY_QUESTIONS>() {
                        @Override
                        public void handleResponse(CHEMISTRY_QUESTIONS response) {
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
                            insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
                            insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
                            insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
                            insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());

                            Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                            //StartActivity(bundle ,context);
                            System.out.println("bundle from the middle tier : " + insideBundle);
                            Intent i = new Intent(context, questionActivity.class);
                            i.putExtras(insideBundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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

    public static void get_geography_questions(int QuestionID, final Context context) {


        String whereClause = " ID=" + QuestionID;
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of(GEOGRAPHY_QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<GEOGRAPHY_QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<GEOGRAPHY_QUESTIONS> foundQuestions) {
                for (GEOGRAPHY_QUESTIONS q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(GEOGRAPHY_QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<GEOGRAPHY_QUESTIONS>() {
                        @Override
                        public void handleResponse(GEOGRAPHY_QUESTIONS response) {
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
                            insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
                            insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
                            insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
                            insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());

                            Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                            //StartActivity(bundle ,context);
                            System.out.println("bundle from the middle tier : " + insideBundle);
                            Intent i = new Intent(context, questionActivity.class);
                            i.putExtras(insideBundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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

    public static void get_astronomy_questions(int QuestionID, final Context context) {


        String whereClause = " ID=" + QuestionID;
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of(ASTRONOMY_QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<ASTRONOMY_QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<ASTRONOMY_QUESTIONS> foundQuestions) {
                for (ASTRONOMY_QUESTIONS q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(ASTRONOMY_QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<ASTRONOMY_QUESTIONS>() {
                        @Override
                        public void handleResponse(ASTRONOMY_QUESTIONS response) {
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
                            insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
                            insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
                            insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
                            insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());

                            Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                            //StartActivity(bundle ,context);
                            System.out.println("bundle from the middle tier : " + insideBundle);
                            Intent i = new Intent(context, questionActivity.class);
                            i.putExtras(insideBundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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

    public static void get_literature_questions(int QuestionID, final Context context) {


        String whereClause = " ID=" + QuestionID;
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of(LITERATURE_QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<LITERATURE_QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<LITERATURE_QUESTIONS> foundQuestions) {
                for (LITERATURE_QUESTIONS q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(LITERATURE_QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<LITERATURE_QUESTIONS>() {
                        @Override
                        public void handleResponse(LITERATURE_QUESTIONS response) {
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
                            insideBundle.putString("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
                            insideBundle.putString("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));
                            insideBundle.putString("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
                            insideBundle.putString("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());

                            Log.d(TAG, "trying to fetch questions from DB inside the handle Response method   " + insideBundle);
                            //StartActivity(bundle ,context);
                            System.out.println("bundle from the middle tier : " + insideBundle);
                            Intent i = new Intent(context, questionActivity.class);
                            i.putExtras(insideBundle);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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
