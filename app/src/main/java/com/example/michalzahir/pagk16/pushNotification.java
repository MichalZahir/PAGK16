package com.example.michalzahir.pagk16;

import android.content.Context;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PushBroadcastMask;
import com.backendless.messaging.PushPolicyEnum;

/**
 * Created by zahirm on 2016-06-02.
 */
public class pushNotification {
    private static final String TAG = "Push Notification";


public static void PublishNotification(Context c){

    final String appVersion = "v1";
    DeliveryOptions deliveryOptions = new DeliveryOptions();
    deliveryOptions.setPushPolicy(PushPolicyEnum.ONLY );
    deliveryOptions.addPushSinglecast( "LGH440nce43a48f" );
    //deliveryOptions.addPushSinglecast( "unknown");
    deliveryOptions.setPushBroadcast( PushBroadcastMask.ANDROID  );

    PublishOptions publishOptions = new PublishOptions();
    publishOptions.setPublisherId("michael");
    publishOptions.setSubtopic("Zahiiiir");
    publishOptions.putHeader( "android-ticker-text", "You just got a private push notification!" );
    publishOptions.putHeader( "android-content-title", "This is a notification title" );
    publishOptions.putHeader( "android-content-text", "Push Notifications are cool" );
    // MessageStatus status =Backendless.Messaging.publish( "default","this is a private message!", publishOptions, deliveryOptions) ;
    Backendless.Messaging.publish("this is a private message!", publishOptions, deliveryOptions, new AsyncCallback<MessageStatus>() {
        @Override
        public void handleResponse(MessageStatus messageStatus) {
            Log.d(TAG, "Push Notification  workin. status :   " + messageStatus +"Error"+ messageStatus.getErrorMessage() + "The Message ID "+messageStatus.getMessageId());
        }

        @Override
        public void handleFault(BackendlessFault fault) {
            Log.d(TAG, "Push Notification not workin .  The Cause :   " + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );
        }
    }) ;





}
}
