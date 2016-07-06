package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
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
    static String OpponentDeviceID;



    public static void setQuestionBundle(Bundle questionBundle) {
        QuestionBundle = questionBundle;
    }


    public static void PublishNotification(final Context c, Bundle bundle) {

        final String appVersion = "v1";
        final DeliveryOptions deliveryOptions = new DeliveryOptions();
        deliveryOptions.setPushPolicy(PushPolicyEnum.ALSO);
        //deliveryOptions.addPushSinglecast("LGH440nce43a48f");
        //deliveryOptions.addPushSinglecast("unknown");


        deliveryOptions.addPushSinglecast(OpponentDeviceID);
        //deliveryOptions.setPushBroadcast(PushBroadcastMask.ANDROID);

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

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "Push Notification not workin .  The Cause :   " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
            }
        });


    }

    public static void PublishTheLastResultNotificaton(final Context c, Bundle bundle) {

        final String appVersion = "v1";
        final DeliveryOptions deliveryOptions = new DeliveryOptions();
        deliveryOptions.setPushPolicy(PushPolicyEnum.ALSO);
         //deliveryOptions.addPushSinglecast("LGH440nce43a48f");
        //deliveryOptions.addPushSinglecast("unknown");
        deliveryOptions.addPushSinglecast(OpponentDeviceID);
        deliveryOptions.setPushBroadcast(PushBroadcastMask.ANDROID);

        PublishOptions publishOptions = new PublishOptions();
        publishOptions.setPublisherId("michael");
        publishOptions.setSubtopic("Zahiiiir");
        publishOptions.putHeader("1st user result", bundle.getString("1st user result"));
        publishOptions.putHeader("2nd user result", bundle.getString("2nd user result"));
        publishOptions.putHeader("Last Result", "Last Result");

        publishOptions.putHeader("firstUSerObjectID", NewGameActivity.result.getFirstUSerObjectID());
        publishOptions.putHeader("secondUSerObjectID", NewGameActivity.result.getSecondUSerObjectID());
        publishOptions.putHeader("firstUserResult", String.valueOf(NewGameActivity.result.getFirstUserResult()));
        publishOptions.putHeader("secondtUserResult", String.valueOf(NewGameActivity.result.getSecondtUserResult()));


        publishOptions.putHeader("android-ticker-text", "Your oponent just finished!");
        publishOptions.putHeader("android-content-title", "PAGK");
        publishOptions.putHeader("android-content-text", "We got the final result, check if you won or lost!");
        // MessageStatus status =Backendless.Messaging.publish( "default","this is a private message!", publishOptions, deliveryOptions) ;
        //retrieveDane(c);
        Backendless.Messaging.publish("default", "this is a private message!", publishOptions, deliveryOptions, new AsyncCallback<MessageStatus>() {
            @Override
            public void handleResponse(MessageStatus messageStatus) {
                Log.d(TAG, "Push Notification  workin. status :   " + messageStatus + "Error" + messageStatus.getErrorMessage() + "The Message ID " + messageStatus.getMessageId() + "the device receiver is : " + deliveryOptions.getPushSinglecast());

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "Push Notification not workin .  The Cause :   " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
            }
        });


    }

    public static void GetOpponentUserObjID() {


        if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getFirstUSerObjectID())) {
            Log.d(TAG, "searching for the device ID for the Following User Object ID  = :   " + NewGameActivity.result.getSecondUSerObjectID() );
            GetReceiverDeviceID(NewGameActivity.result.getSecondUSerObjectID());

        } else if (playerObejtID.getUserObjectID().equals(NewGameActivity.result.getSecondUSerObjectID())) {
            Log.d(TAG, "searching for the device ID for the Following User Object ID  = :   " + NewGameActivity.result.getFirstUSerObjectID() );
            GetReceiverDeviceID(NewGameActivity.result.getFirstUSerObjectID());

        }

    }

    public static void GetReceiverDeviceID(final String OpponentUserObjectID) {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Backendless.UserService.findById  (OpponentUserObjectID, new AsyncCallback<BackendlessUser>() { @Override
                                 public void handleResponse(BackendlessUser backendlessUser )
                                 {
                                    System.out.println(backendlessUser.getObjectId());
                                    OpponentDeviceID = (String) backendlessUser.getProperty("Device_ID");



                                }

                                    @Override
                                    public void handleFault( BackendlessFault fault )
                                    {
                                        Log.d(TAG, "The device ID was Not found in the user table:   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
                                    }});

                }catch (BackendlessException e)
                    {
                        Log.d(TAG, "The device ID was Not found in the user table:   " + e.getMessage() + e.getDetail() + e.getCode() + "for the user object ID " +OpponentUserObjectID );                }
                }
            });
        thread.start();

        //        Backendless.UserService.findById  (OpponentUserObjectID, new AsyncCallback<BackendlessUser>() { @Override
        //        public void handleResponse(BackendlessUser backendlessUser )
        //        {
        //            System.out.println(backendlessUser.getObjectId());
        //            OpponentDeviceID = (String) backendlessUser.getProperty("Device_ID");
        //
        //
        //
        //        }
        //
        //            @Override
        //            public void handleFault( BackendlessFault fault )
        //            {
        //                Log.d(TAG, "The device ID was Not found in the user table:   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
        //            }});
//        Thread thread = new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    //Your code goes here
//                    BackendlessUser backendlessUser = Backendless.UserService.findById(OpponentUserObjectID);
//                    Log.d(TAG, "searching for the device ID for the Following User Object ID  = :   " + OpponentUserObjectID );
//                    OpponentDeviceID = (String) backendlessUser.getProperty("Device_ID");
//
//                    Log.d(TAG, "The device ID was found = :   " + OpponentDeviceID );
//                }
//                catch (BackendlessException e)
//                {
//                    Log.d(TAG, "The device ID was Not found in the user table:   " + e.getMessage() + e.getDetail() + e.getCode() + "for the user object ID " +OpponentUserObjectID );                }
//            }
//        });
//
//        thread.start();

//        try {
//            BackendlessUser backendlessUser = Backendless.UserService.findById(OpponentUserObjectID);
//            Log.d(TAG, "searching for the device ID for the Following User Object ID  = :   " + OpponentUserObjectID );
//            OpponentDeviceID = (String) backendlessUser.getProperty("Device_ID");
//
//            Log.d(TAG, "The device ID was found = :   " + OpponentDeviceID );
//        }
//        catch (BackendlessException e){
//            Log.d(TAG, "The device ID was Not found in the user table:   " + e.getMessage() + e.getDetail() + e.getCode() + "for the user object ID " +OpponentUserObjectID );
//        }

    }

}

