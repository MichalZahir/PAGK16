package com.example.michalzahir.pagk16;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.PublishOptions;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.push.BackendlessBroadcastReceiver;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.SAVED_QUESTIONS;


public class PushReceiver extends BackendlessBroadcastReceiver {
    static Intent notificationIntent;
    Bundle insideBundle = new Bundle();


    @Override
    public boolean onMessage(Context context, Intent intent) {
        CharSequence tickerText = intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
        CharSequence contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
        CharSequence contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);
        String subtopic = intent.getStringExtra("message");
        Bundle bundle = intent.getExtras();

        Bundle notificationBundle = new Bundle();
        notificationBundle.putString("Question", bundle.getString("Question"));
        notificationBundle.putString("Answer_A", bundle.getString("Answer_A"));
        notificationBundle.putString("Answer_B", bundle.getString("Answer_B"));
        notificationBundle.putString("Answer_C", bundle.getString("Answer_C"));
        notificationBundle.putString("Answer_D", bundle.getString("Answer_D"));
        Boolean Correct_A = null;
        if (bundle.getString("correct_A").equals("1"))
            Correct_A = true;
        else if (bundle.getString("correct_A").equals("0"))
            Correct_A = false;
        Boolean Correct_B = null;
        if (bundle.getString("correct_B").equals("1"))
            Correct_B = true;
        else if (bundle.getString("correct_B").equals("0"))
            Correct_B = false;
        Boolean Correct_C = null;
        if (bundle.getString("correct_C").equals("1"))
            Correct_C = true;
        else if (bundle.getString("correct_C").equals("0"))
            Correct_C = false;

        Boolean Correct_D = null;
        if (bundle.getString("correct_D").equals("1"))
            Correct_D = true;
        else if (bundle.getString("correct_D").equals("0"))
            Correct_D = false;


        notificationBundle.putBoolean("correct_A",Correct_A );
        notificationBundle.putBoolean("correct_B",Correct_B );
        notificationBundle.putBoolean("correct_C",Correct_C );
        notificationBundle.putBoolean("correct_D",Correct_D );

        notificationBundle.putString("firstUSerObjectID", bundle.getString("firstUSerObjectID"));
        notificationBundle.putString("secondUSerObjectID", bundle.getString("secondUSerObjectID"));
        notificationBundle.putString("firstUserResult", bundle.getString("firstUserResult"));
        notificationBundle.putString("secondtUserResult", bundle.getString("secondtUserResult"));

        //pushNotification.retrieveDane(context);
        //retrieveDane();

        //pushNotification.retrieveDane(context);
        if (tickerText != null && tickerText.length() > 0) {
            int appIcon = R.mipmap.ic_launcher;
            if (appIcon == 0)
                appIcon = android.R.drawable.sym_def_app_icon;


//
//                           SavedQuestionsToBundle(RecyclerAdapter.savedquestions.getSavedQuestions());
            notificationIntent = new Intent(context, questionActivity.class);
            System.out.println("The current bundle is  from the push receiver why is it empty:     " + notificationBundle);
            notificationIntent.putExtras(notificationBundle);

            notificationIntent.putExtra("subtopic", subtopic);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            notificationBuilder.setSmallIcon(getNotificationIcon());
            notificationBuilder.setTicker(tickerText);
            notificationBuilder.setWhen(System.currentTimeMillis());
            notificationBuilder.setContentTitle(contentTitle);
            notificationBuilder.setContentText(contentText);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(contentIntent);


            Notification notification = notificationBuilder.build();


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);


        }

        return false;
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.notification_pagk : R.mipmap.ic_launcher;
    }

    public void retrieveDane() {
        Backendless.Persistence.of(SAVED_QUESTIONS.class).find(new AsyncCallback<BackendlessCollection<SAVED_QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<SAVED_QUESTIONS> foundContacts) {
                // all Contact instances have been found
                for (SAVED_QUESTIONS q : foundContacts.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getComment()) ;

                    Backendless.Persistence.of(SAVED_QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<SAVED_QUESTIONS>() {
                        @Override
                        public void handleResponse(SAVED_QUESTIONS response) {
                            // a Contact instance has been found by ObjectId

                            int inforamycjny = response.getID();
                            System.out.print("Airi b al nbeee" + response.getID());
                            get_Saved_Question(response.getID());


                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d("push reciever", "User not updated (Device ID Update ) for the reasons" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                        }
                    });


                    // + q.getAnswer_a()+ q.getAnswer_b()+q.getAnswer_b(),q.getAnswer_c(),q.getAnswer_d(),q.getCorrect_a(),q.getCorrect_b(),q.getCorrect_c(),q.getCorrect_d()
                }
            }

            @Override
            public void handleFault(BackendlessFault fault)

            {
                Log.d("push reciever", "User not updated (Device ID Update ) for the reasons" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

            }
        });


    }

    public void get_Saved_Question(int IDD) {


        int ID = IDD;
        System.out.println("ID ffrom get saved questions" + ID);
        String whereClause = " ID=" + ID;
        //Saved_Question.setID(ID);
        //save_question_for_scnd_usr(Saved_Question);
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        //BackendlessCollection<QUESTIONS> result =
        Backendless.Persistence.of(QUESTIONS.class).find(dataQuery, new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions) {
                for (QUESTIONS q : foundQuestions.getData()) {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of(QUESTIONS.class).findById(q.getObjectId(), new AsyncCallback<QUESTIONS>() {
                        @Override
                        public void handleResponse(QUESTIONS response) {
                            // a Contact instance has been found by ObjectId


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
                            //setQuestionBundle(insideBundle);
                            //List<QUESTIONS> savedQuestions = new ArrayList<>();
                            // savedquestions = new SavedQuestions(savedQuestions);
                            //savedquestions.addToSavedQuestions(response);
                            //Log.d(TAG, "second user bundle " + insideBundle);
                            //StartActivity(bundle ,context);
                            System.out.println("bundle from the second user : " + insideBundle);
                            // Intent i = new Intent(c, questionActivity.class);
                            // i.putExtras(QuestionBundle);
                            // c.startActivity(i);

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d("push reciever", "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
                        }
                    });
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("push reciever", "fault trying to fetch questions from DB fault" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

            }
        });
    }
}