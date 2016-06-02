package com.example.michalzahir.pagk16;

import android.content.Context;

import com.backendless.Backendless;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PushPolicyEnum;

/**
 * Created by zahirm on 2016-06-02.
 */
public class pushNotification {


public static void PublishNotification(Context c){

    final String appVersion = "v1";
    Backendless.initApp( c,"49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
    DeliveryOptions deliveryOptions = new DeliveryOptions();
    deliveryOptions.setPushPolicy(PushPolicyEnum.ONLY );
    deliveryOptions.addPushSinglecast( "LGH440nce43a48f" );

    PublishOptions publishOptions = new PublishOptions();
    publishOptions.putHeader( "android-ticker-text", "You just got a private push notification!" );
    publishOptions.putHeader( "android-content-title", "This is a notification title" );
    publishOptions.putHeader( "android-content-text", "Push Notifications are cool" );

    MessageStatus status = Backendless.Messaging.publish( "default","this is a private message!", publishOptions, deliveryOptions );





}
}
