package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PublishStatusEnum;
import com.backendless.messaging.PushBroadcastMask;
import com.backendless.messaging.PushPolicyEnum;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.SAVED_QUESTIONS;
import com.example.michalzahir.pagk16.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zahirm on 2016-06-02.
 */
public class pushNotification {
    private static final String TAG = "Push Notification";
    static Bundle QuestionBundle = new Bundle();


    public static void setQuestionBundle(Bundle questionBundle) {
        QuestionBundle = questionBundle;
    }


    public static void PublishNotification(final Context c, Bundle bundle) {

        final String appVersion = "v1";
        final DeliveryOptions deliveryOptions = new DeliveryOptions();
        deliveryOptions.setPushPolicy(PushPolicyEnum.ALSO);
        deliveryOptions.addPushSinglecast("LGH440nce43a48f");
        deliveryOptions.addPushSinglecast("unknown");
        deliveryOptions.setPushBroadcast(PushBroadcastMask.ANDROID);

        PublishOptions publishOptions = new PublishOptions();
        publishOptions.setPublisherId("michael");
        publishOptions.setSubtopic("Zahiiiir");
        publishOptions.putHeader("Question", bundle.getString("Question"));
        publishOptions.putHeader("Answer_A", bundle.getString("Answer_A"));
        publishOptions.putHeader("Answer_B", bundle.getString("Answer_B"));
        publishOptions.putHeader("Answer_C", bundle.getString("Answer_C"));
        publishOptions.putHeader("Answer_D", bundle.getString("Answer_D"));
        String Correct_A = null;
        if (bundle.getBoolean("correct_A") == true)
            Correct_A = "1";
        else if (bundle.getBoolean("correct_A") == false)
            Correct_A = "0";
        String Correct_B = null;
        if (bundle.getBoolean("correct_B") == true)
            Correct_B = "1";
        else if (bundle.getBoolean("correct_B") == false)
            Correct_B = "0";
        String Correct_C = null;
        if (bundle.getBoolean("correct_C") == true)
            Correct_C = "1";
        else if (bundle.getBoolean("correct_C") == false)
            Correct_C = "0";
        String Correct_D = null;
        if (bundle.getBoolean("correct_D") == true)
            Correct_D = "1";
        else if (bundle.getBoolean("correct_D") == false)
            Correct_D = "0";
        publishOptions.putHeader("correct_A", Correct_A);
        publishOptions.putHeader("correct_B", Correct_B);
        publishOptions.putHeader("correct_C", Correct_C);
        publishOptions.putHeader("correct_D", Correct_D);
        publishOptions.putHeader("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
        publishOptions.putHeader("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());
        publishOptions.putHeader("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
        publishOptions.putHeader("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));


        publishOptions.putHeader("android-ticker-text", "You just got a private push notification!");
        publishOptions.putHeader("android-content-title", "PAGK");
        publishOptions.putHeader("android-content-text", "Your oponent just finished, It's your turn to play");
        // MessageStatus status =Backendless.Messaging.publish( "default","this is a private message!", publishOptions, deliveryOptions) ;
        //retrieveDane(c);
        Backendless.Messaging.publish("default", "this is a private message!", publishOptions, deliveryOptions, new AsyncCallback<MessageStatus>() {
            @Override
            public void handleResponse(MessageStatus messageStatus) {
                Log.d(TAG, "Push Notification  workin. status :   " + messageStatus + "Error" + messageStatus.getErrorMessage() + "The Message ID " + messageStatus.getMessageId() + "the device receiver is : " + deliveryOptions.getPushSinglecast());
                //   if( messageStatus.equals( PublishStatusEnum.SCHEDULED ))
                // {

//                List<QUESTIONS> savedQuestions = RecyclerAdapter.savedquestions.getSavedQuestions();
//
//
//
//                for (int i = 0 ; i< savedQuestions.size();i++) {
//                    System.out.println("this is the question from the backendless DB  " + savedQuestions.get(i).getQuestion()
//                            + ".    this is the first answer   " + savedQuestions.get(i).getAnswer_a() + ".   Hurrraaa success !!!!" + savedQuestions.get(i).getCORRECT_A() + " B boolean:" + savedQuestions.get(i).getCORRECT_B() + " D boolean:" + savedQuestions.get(i).getCORRECT_D() + " C boolean:" + savedQuestions.get(i).getCORRECT_C() + "AA" + savedQuestions.get(i).getAnswer_a() + "bA" + savedQuestions.get(i).getANSWER_B() + "cA" + savedQuestions.get(i).getANSWER_C() + "DA" + savedQuestions.get(i).getANSWER_D());
//                    QuestionBundle.putString("Question", savedQuestions.get(i).getQuestion());
//                    QuestionBundle.putString("Answer_A", savedQuestions.get(i).getAnswer_a());
//                    QuestionBundle.putString("Answer_B", savedQuestions.get(i).getANSWER_B());
//                    QuestionBundle.putString("Answer_C", savedQuestions.get(i).getANSWER_C());
//                    QuestionBundle.putString("Answer_D", savedQuestions.get(i).getANSWER_D());
//                    QuestionBundle.putBoolean("correct_A", savedQuestions.get(i).getCORRECT_A());
//                    QuestionBundle.putBoolean("correct_B", savedQuestions.get(i).getCORRECT_B());
//                    QuestionBundle.putBoolean("correct_C", savedQuestions.get(i).getCORRECT_C());
//                    QuestionBundle.putBoolean("correct_D", savedQuestions.get(i).getCORRECT_D());
//                    System.out.println(" push notification sender !! bundle : The current bundle is  from the push receiver why is it empty:     "+pushNotification.QuestionBundle);
//                }

                // }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "Push Notification not workin .  The Cause :   " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
            }
        });


    }

    public void SavedQuestionsToBundle(List<QUESTIONS> savedQuestions) {


        for (int i = 0; i < savedQuestions.size(); i++) {
            System.out.println("this is the question from the backendless DB  " + savedQuestions.get(i).getQuestion()
                    + ".    this is the first answer   " + savedQuestions.get(i).getAnswer_a() + ".   Hurrraaa success !!!!" + savedQuestions.get(i).getCORRECT_A() + " B boolean:" + savedQuestions.get(i).getCORRECT_B() + " D boolean:" + savedQuestions.get(i).getCORRECT_D() + " C boolean:" + savedQuestions.get(i).getCORRECT_C() + "AA" + savedQuestions.get(i).getAnswer_a() + "bA" + savedQuestions.get(i).getANSWER_B() + "cA" + savedQuestions.get(i).getANSWER_C() + "DA" + savedQuestions.get(i).getANSWER_D());
            //QuestionBundle = new Bundle();
            QuestionBundle.putString("Question", savedQuestions.get(i).getQuestion());
            QuestionBundle.putString("Answer_A", savedQuestions.get(i).getAnswer_a());
            QuestionBundle.putString("Answer_B", savedQuestions.get(i).getANSWER_B());
            QuestionBundle.putString("Answer_C", savedQuestions.get(i).getANSWER_C());
            QuestionBundle.putString("Answer_D", savedQuestions.get(i).getANSWER_D());
            QuestionBundle.putBoolean("correct_A", savedQuestions.get(i).getCORRECT_A());
            QuestionBundle.putBoolean("correct_B", savedQuestions.get(i).getCORRECT_B());
            QuestionBundle.putBoolean("correct_C", savedQuestions.get(i).getCORRECT_C());
            QuestionBundle.putBoolean("correct_D", savedQuestions.get(i).getCORRECT_D());

        }
    }


}