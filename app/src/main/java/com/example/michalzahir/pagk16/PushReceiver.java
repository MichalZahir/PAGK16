package com.example.michalzahir.pagk16;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
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
import com.example.michalzahir.pagk16.FacebookUsers.fbFriendsListActivity;
import com.example.michalzahir.pagk16.Helper.chatMessage;
import com.example.michalzahir.pagk16.fakeActivity.ActivityFake;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PushReceiver extends BackendlessBroadcastReceiver {
    static Intent notificationIntent;
    Bundle insideBundle = new Bundle();
    static Intent LastResultIntent;
    public  static List<chatMessage> ChatMessages =new ArrayList<>();

    @Override
    public boolean onMessage(Context context, Intent intent) {
        CharSequence tickerText = intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
        CharSequence contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
        CharSequence contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);

        String subtopic = intent.getStringExtra("message");
        Bundle bundle = intent.getExtras();

        if (bundle.containsKey("UserName")) {
            SetUserNameoppName(bundle);
        }
        if(bundle.containsKey("Chat")){
            OnChatMessage(bundle, intent, context);
        }

        // in this place put the if clause to see whether is it a notification with last result or its the notification with the questions.
        else if (bundle.containsKey("Last Result")) {

            GetLastResultNotification(bundle, intent, context);

        } else {

            if (bundle.containsKey("QuestionIDS") && bundle.containsKey("FB_game")) {
                Get_FB_First_Round(bundle, intent, context);
                pushNotification.GetOpponentUserObjID(context);

            } else if ( bundle.containsKey("RankingGame")){

                Get_RankingGame_First_Round(bundle, intent, context);
                pushNotification.GetOpponentUserObjID(context);
            }
            else if (bundle.containsKey("QuestionIDS")) {

                InitialiseGameResultWhenNull();
                NewGameActivity.result.setFirstUSerObjectID(bundle.getString("firstUSerObjectID"));
                NewGameActivity.result.setSecondUSerObjectID(bundle.getString("secondUSerObjectID"));
                NewGameActivity.result.setFirstUserResult(Integer.parseInt(bundle.getString("firstUserResult")));
                NewGameActivity.result.setSecondtUserResult(Integer.parseInt(bundle.getString("secondtUserResult")));

                com.example.michalzahir.pagk16.Helper.UserQueueQuestionRetriever.RetrieveQuestionForFirstRound(bundle.getString("QuestionIDS"), context);

            } else {


                Bundle notificationBundle = new Bundle();
                GetFbGameAddToQueu(bundle, notificationBundle);
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


                notificationBundle.putBoolean("correct_A", Correct_A);
                notificationBundle.putBoolean("correct_B", Correct_B);
                notificationBundle.putBoolean("correct_C", Correct_C);
                notificationBundle.putBoolean("correct_D", Correct_D);

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
                    Random random = new Random();
                    int m = random.nextInt(9999 - 1000) + 1000;
                    notificationIntent.putExtra("subtopic", subtopic);
                    PendingIntent contentIntent = PendingIntent.getActivity(context, m, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                    notificationBuilder.setSmallIcon(getNotificationIcon());
                    notificationBuilder.setTicker(tickerText);
                    notificationBuilder.setWhen(System.currentTimeMillis());
                    notificationBuilder.setContentTitle(contentTitle);
                    notificationBuilder.setContentText(contentText);
                    notificationBuilder.setAutoCancel(true);
                    notificationBuilder.setContentIntent(contentIntent);
                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    notificationBuilder.setSound(uri);
                    //notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                    //notificationBuilder.setLights(Color.RED, 3000, 3000);

                    Notification notification = notificationBuilder.build();


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(m, notification);

                }

            }
        }

        return false;

    }

    public void GetLastResultNotification(Bundle bundle, Intent intent, Context context) {

        Bundle notificationBundle = new Bundle();
        //notificationBundle.putString("1st user result", bundle.getString("1st user result"));
        //notificationBundle.putString("2nd user result", bundle.getString("2nd user result"));
        GetFbGameAddToQueu(bundle, notificationBundle);
        notificationBundle.putInt("1st user result", Integer.parseInt(bundle.getString("firstUserResult")));
        notificationBundle.putInt("2nd user result", Integer.parseInt(bundle.getString("secondtUserResult")));
        notificationBundle.putString("Last Result", "Last Result");
        notificationBundle.putString("firstUSerObjectID", bundle.getString("firstUSerObjectID"));
        notificationBundle.putString("secondUSerObjectID", bundle.getString("secondUSerObjectID"));
        notificationBundle.putString("UserName", MainActivity.userName.getUserName());
        notificationBundle.putString("UserNameUSrObjectID", MainActivity.userName.getUserNameUSrObjectID());

        if (MainActivity.userName.getOponnentUserObjectID() != null) {
            notificationBundle.putString("OpponentName", MainActivity.userName.getOponnentName());
            notificationBundle.putString("OpponentUserObjectID", MainActivity.userName.getOponnentUserObjectID());
        }
        CharSequence tickerText = intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
        CharSequence contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
        CharSequence contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);
        String subtopic = intent.getStringExtra("message");
        if (tickerText != null && tickerText.length() > 0) {
            int appIcon = R.mipmap.ic_launcher;
            if (appIcon == 0)
                appIcon = android.R.drawable.sym_def_app_icon;


//
//                           SavedQuestionsToBundle(RecyclerAdapter.savedquestions.getSavedQuestions());
            LastResultIntent = new Intent(context, resultActivity.class);
            System.out.println("The current bundle is  from the push receiver why is it empty:     " + notificationBundle);
            LastResultIntent.putExtras(notificationBundle);
            Random random = new Random();
            int m = random.nextInt(9999 - 1000) + 1000;
            LastResultIntent.putExtra("subtopic", subtopic);
            PendingIntent contentIntent = PendingIntent.getActivity(context, m, LastResultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            notificationBuilder.setSmallIcon(getNotificationIcon());
            notificationBuilder.setTicker(tickerText);
            notificationBuilder.setWhen(System.currentTimeMillis());
            notificationBuilder.setContentTitle(contentTitle);
            notificationBuilder.setContentText(contentText);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(contentIntent);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(uri);
            //notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
            //notificationBuilder.setLights(Color.RED, 3000, 3000);
            Notification notification = notificationBuilder.build();


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(m, notification);

        }
    }

    public void GetFbGameAddToQueu(Bundle bundle, Bundle NotificationBundle) {
        if (bundle.containsKey("FB_game")) {
            NotificationBundle.putBoolean("FB_game", true);
            NotificationBundle.putBoolean("AddUserToQueue", false);

        }
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

    public void Get_FB_First_Round(final Bundle bundle, Intent intent, Context context) {
        CharSequence tickerText = intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
        CharSequence contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
        CharSequence contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);
        String subtopic = intent.getStringExtra("message");
        final String appVersion = "v1";
        Backendless.initApp(context.getApplicationContext(), "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
        InitialiseGameResultWhenNull();
        fbFriendsListActivity.FbGame = true;
        gameResult.questionsAnswered =0;

        Bundle notificationBundle = new Bundle();
        GetFbGameAddToQueu(bundle, notificationBundle);
        notificationBundle.putString("Question", bundle.getString("Question"));
        notificationBundle.putString("Answer_A", bundle.getString("Answer_A"));
        notificationBundle.putString("Answer_B", bundle.getString("Answer_B"));
        notificationBundle.putString("Answer_C", bundle.getString("Answer_C"));
        notificationBundle.putString("Answer_D", bundle.getString("Answer_D"));
        notificationBundle.putString("UserName", MainActivity.userName.getUserName());
        notificationBundle.putString("UserNameUSrObjectID", MainActivity.userName.getUserNameUSrObjectID());

        if (MainActivity.userName.getOponnentUserObjectID() != null) {
            notificationBundle.putString("OpponentName", MainActivity.userName.getOponnentName());
            notificationBundle.putString("OpponentUserObjectID", MainActivity.userName.getOponnentUserObjectID());
        }
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


        notificationBundle.putBoolean("correct_A", Correct_A);
        notificationBundle.putBoolean("correct_B", Correct_B);
        notificationBundle.putBoolean("correct_C", Correct_C);
        notificationBundle.putBoolean("correct_D", Correct_D);
        NewGameActivity.AddUserToQueue = false;
        notificationBundle.putString("firstUSerObjectID", bundle.getString("firstUSerObjectID"));
        notificationBundle.putString("secondUSerObjectID", bundle.getString("secondUSerObjectID"));
        notificationBundle.putString("firstUserResult", bundle.getString("firstUserResult"));
        notificationBundle.putString("secondtUserResult", bundle.getString("secondtUserResult"));
        notificationBundle.putString("QuestionIDS", bundle.getString("QuestionIDS"));
        NewGameActivity.result.setFirstUSerObjectID(bundle.getString("firstUSerObjectID"));
        NewGameActivity.result.setSecondUSerObjectID(bundle.getString("secondUSerObjectID"));
        NewGameActivity.result.setFirstUserResult(Integer.parseInt(bundle.getString("firstUserResult")));
        NewGameActivity.result.setSecondtUserResult(Integer.parseInt(bundle.getString("secondtUserResult")));
        notificationIntent = new Intent(context, ActivityFake.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtras(notificationBundle);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        PendingIntent contentIntent = PendingIntent.getActivity(context, m, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(getNotificationIcon());
        notificationBuilder.setTicker(tickerText);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentTitle(contentTitle);
        notificationBuilder.setContentText(contentText);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(contentIntent);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(uri);
        //notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        //notificationBuilder.setLights(Color.RED, 3000, 3000);

        Notification notification = notificationBuilder.build();
        //notification.defaults |= Notification.DEFAULT_VIBRATE;


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
        //notificationManager.notify(0, notification);
        BroadcastReceiver call_method = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action_name = intent.getAction();
                if (action_name.equals("call_method")) {
                    // call your method here and do what ever you want.
                    com.example.michalzahir.pagk16.Helper.UserQueueQuestionRetriever.RetrieveQuestionForFirstRound(bundle.getString("QuestionIDS"), context);

                }
            }
        };
        context.getApplicationContext().registerReceiver(call_method, new IntentFilter("call_method"));


    }

    public static void InitialiseGameResultWhenNull() {
        if (NewGameActivity.result == null) {
            NewGameActivity.result = new gameResult();

        }


    }

    public static void SetUserNameoppName(Bundle bundle) {
        MainActivity.userName = new UserName();
        Log.d("push reciever", "getUserObjectID :" + playerObejtID.getUserObjectID());


        MainActivity.userName.setUserName(bundle.getString("UserName"));
        MainActivity.userName.setOponnentName(bundle.getString("OpponentName"));
        MainActivity.userName.setUserNameUSrObjectID(bundle.getString("UserNameUSrObjectID"));
        MainActivity.userName.setOponnentUserObjectID(bundle.getString("OpponentUserObjectID"));
        Log.d("push reciever", "getUserName :" + MainActivity.userName.getUserName());

        Log.d("push reciever", "getUserNameUSrObjectID :" + MainActivity.userName.getUserNameUSrObjectID());

        Log.d("push reciever", "getOpponentName :" + MainActivity.userName.getOponnentName());
        Log.d("push receiver", "getOpponentUserObjectID :" + MainActivity.userName.getOponnentUserObjectID());
    }
    public void Get_RankingGame_First_Round(final Bundle bundle, Intent intent, Context context) {
        CharSequence tickerText = intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
        CharSequence contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
        CharSequence contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);
        String subtopic = intent.getStringExtra("message");
        final String appVersion = "v1";
        Backendless.initApp(context.getApplicationContext(), "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
        InitialiseGameResultWhenNull();
        fbFriendsListActivity.FbGame = false;
        gameResult.questionsAnswered =0;

        Bundle notificationBundle = new Bundle();
        GetFbGameAddToQueu(bundle, notificationBundle);
        notificationBundle.putString("Question", bundle.getString("Question"));
        notificationBundle.putString("Answer_A", bundle.getString("Answer_A"));
        notificationBundle.putString("Answer_B", bundle.getString("Answer_B"));
        notificationBundle.putString("Answer_C", bundle.getString("Answer_C"));
        notificationBundle.putString("Answer_D", bundle.getString("Answer_D"));
        notificationBundle.putString("UserName", MainActivity.userName.getUserName());
        notificationBundle.putString("UserNameUSrObjectID", MainActivity.userName.getUserNameUSrObjectID());

        if (MainActivity.userName.getOponnentUserObjectID() != null) {
            notificationBundle.putString("OpponentName", MainActivity.userName.getOponnentName());
            notificationBundle.putString("OpponentUserObjectID", MainActivity.userName.getOponnentUserObjectID());
        }
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


        notificationBundle.putBoolean("correct_A", Correct_A);
        notificationBundle.putBoolean("correct_B", Correct_B);
        notificationBundle.putBoolean("correct_C", Correct_C);
        notificationBundle.putBoolean("correct_D", Correct_D);
        NewGameActivity.AddUserToQueue = false;
        notificationBundle.putBoolean("AddUserToQueue",false);
        notificationBundle.putString("firstUSerObjectID", bundle.getString("firstUSerObjectID"));
        notificationBundle.putString("secondUSerObjectID", bundle.getString("secondUSerObjectID"));
        notificationBundle.putString("firstUserResult", bundle.getString("firstUserResult"));
        notificationBundle.putString("secondtUserResult", bundle.getString("secondtUserResult"));
        notificationBundle.putString("QuestionIDS", bundle.getString("QuestionIDS"));
        NewGameActivity.result.setFirstUSerObjectID(bundle.getString("firstUSerObjectID"));
        NewGameActivity.result.setSecondUSerObjectID(bundle.getString("secondUSerObjectID"));
        NewGameActivity.result.setFirstUserResult(Integer.parseInt(bundle.getString("firstUserResult")));
        NewGameActivity.result.setSecondtUserResult(Integer.parseInt(bundle.getString("secondtUserResult")));
        notificationIntent = new Intent(context, ActivityFake.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtras(notificationBundle);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        PendingIntent contentIntent = PendingIntent.getActivity(context, m, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(getNotificationIcon());
        notificationBuilder.setTicker(tickerText);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notificationBuilder.setContentTitle(contentTitle);
        notificationBuilder.setContentText(contentText);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(contentIntent);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(uri);
        //notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        //notificationBuilder.setLights(Color.RED, 3000, 3000);

        Notification notification = notificationBuilder.build();
        //notification.defaults |= Notification.DEFAULT_VIBRATE;


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
        //notificationManager.notify(0, notification);
        BroadcastReceiver call_method = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action_name = intent.getAction();
                if (action_name.equals("call_method")) {
                    // call your method here and do what ever you want.
                    com.example.michalzahir.pagk16.Helper.UserQueueQuestionRetriever.RetrieveQuestionForFirstRound(bundle.getString("QuestionIDS"), context);

                }
            }
        };
        context.getApplicationContext().registerReceiver(call_method, new IntentFilter("call_method"));


    }
    public void OnChatMessage(Bundle bundle,Intent intent,Context context){
       String Subtopic = bundle.getString("DSSubtopic");

        //System.out.println("ChatActivity.onChatWindow" +ChatActivity.onChatWindow + " ChatActivity.subtopic="+ChatActivity.subtopic+ " bundle.getString(subtopic  "+bundle.getString("subtopic"+ " bundle.containsKeynoCheck)"+ bundle.containsKey("noCheck")));
        //Boolean air = null;
        //String khra = bundle.getString("DSSubtopic");
        //if (ChatActivity.subtopic!=null){


        //air = ChatActivity.subtopic.equals(bundle.getString("subtopic"));
        //}
        //Boolean r = air;
        //Boolean air1 = !bundle.containsKey("noCheck");
        //Boolean air2 = ChatActivity.onChatWindow;
        Boolean NoCheck =bundle.containsKey("noCheck");
        if (ChatActivity.onChatWindow && ChatActivity.subtopic.equals(Subtopic)&& !NoCheck){


            }

        else {
                Intent chatIntent = new Intent(context, ChatActivity.class);
                Bundle notificationBundle = new Bundle();
                //notificationBundle.putString("1st user result", bundle.getString("1st user result"));
                //notificationBundle.putString("2nd user result", bundle.getString("2nd user result"));
                Intent mainActivity = new Intent(context, MainActivity.class);
                mainActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                notificationBundle.putBoolean("fromNotification", true);
                notificationBundle.putString("UsrsNamesTab", bundle.getString("UsrsNamesTab"));
                notificationBundle.putString("UsrsobjIDsTab", bundle.getString("UsrsobjIDsTab"));
                notificationBundle.putString("UsrsDeviceIDsTab", bundle.getString("UsrsDeviceIDsTab"));
                notificationBundle.putString("subtopic", Subtopic);

                CharSequence tickerText = intent.getStringExtra(PublishOptions.ANDROID_TICKER_TEXT_TAG);
                CharSequence contentTitle = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TITLE_TAG);
                CharSequence contentText = intent.getStringExtra(PublishOptions.ANDROID_CONTENT_TEXT_TAG);
                String subtopic = intent.getStringExtra("message");
                if (tickerText != null && tickerText.length() > 0) {
                    int appIcon = R.mipmap.ic_launcher;
                    if (appIcon == 0)
                        appIcon = android.R.drawable.sym_def_app_icon;


//
//                           SavedQuestionsToBundle(RecyclerAdapter.savedquestions.getSavedQuestions());
                    //Intent chatIntent = new Intent(context, ChatActivity.class);
                    System.out.println("The current bundle is  from the push receiver why is it empty:     " + notificationBundle);
                    chatIntent.putExtras(notificationBundle);
                    Random random = new Random();
                    int m = random.nextInt(9999 - 1000) + 1000;
                    chatMessage CM =chatMessage.findChatMessageByPublisherID(bundle.getString("MessageSender"),ChatMessages);
                    chatIntent.putExtra("MessageSender",bundle.getString("MessageSender"));
                    if (bundle.containsKey("MessageSender"))
                    if (CM!=null)
                    {
                     m = CM.getNotificationID();
                    }
                    if (NoCheck) {
                        //The notification with the user wants to talk to you.
                        chatIntent.putExtra("subtopic", subtopic);
                        chatMessage ChatMessage = new chatMessage();
                        ChatMessage.setPublisherID(bundle.getString("MessageSender"));
                        ChatMessage.setNotificationID(m);
                        ChatMessages.add(ChatMessage);

                    }
                    else {
                        chatIntent.putExtra("subtopic", Subtopic);
                        String Message  = intent.getStringExtra("message");
                        chatIntent.putExtra("message",Message);
                        chatMessage ChatMessage= chatMessage.findChatMessageByPublisherID(bundle.getString("MessageSender"),ChatMessages);
                        Boolean addToChatMessages = false;
                        if (ChatMessage == null) {
                            ChatMessage = new chatMessage();
                            addToChatMessages = true;
                        }
                        ChatMessage.setPublisherID(bundle.getString("MessageSender"));
                        ChatMessage.setNotificationID(m);
                        ChatMessage.getMessages().add(Message);
                        if (addToChatMessages)
                            ChatMessages.add(ChatMessage);

                    }
                    PendingIntent contentIntent = PendingIntent.getActivity(context, m, chatIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                    notificationBuilder.setSmallIcon(getNotificationIcon());
                    notificationBuilder.setTicker(tickerText);
                    notificationBuilder.setWhen(System.currentTimeMillis());
                    notificationBuilder.setContentTitle(contentTitle);
                    notificationBuilder.setContentText(contentText);
                    notificationBuilder.setAutoCancel(true);
                    notificationBuilder.setContentIntent(contentIntent);
                    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    notificationBuilder.setSound(uri);
                    //notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
                    //notificationBuilder.setLights(Color.RED, 3000, 3000);
                    Notification notification = notificationBuilder.build();


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(m, notification);

                }
                System.out.println("Chat Message");
            }
        //chatIntent.putExtra( "owner", owner );
        //chatIntent.putExtra( "subtopic", subtopic );
       // context.startActivity( chatIntent );

    }
}

