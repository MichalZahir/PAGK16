package com.example.michalzahir.pagk16;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.backendless.messaging.PublishOptions;
import com.backendless.push.BackendlessBroadcastReceiver;
import com.example.michalzahir.pagk16.adapter.RecyclerAdapter;

import java.util.List;

public class PushReceiver extends BackendlessBroadcastReceiver
{
    static Intent notificationIntent;

    @Override
    public boolean onMessage( Context context, Intent intent )
    {
        CharSequence tickerText = intent.getStringExtra( PublishOptions.ANDROID_TICKER_TEXT_TAG );
        CharSequence contentTitle = intent.getStringExtra( PublishOptions.ANDROID_CONTENT_TITLE_TAG );
        CharSequence contentText = intent.getStringExtra( PublishOptions.ANDROID_CONTENT_TEXT_TAG );
        String subtopic = intent.getStringExtra( "message" );

        if( tickerText != null && tickerText.length() > 0 )
        {
            int appIcon = R.mipmap.ic_launcher;
            if( appIcon == 0 )
                appIcon = android.R.drawable.sym_def_app_icon;


//
//                           SavedQuestionsToBundle(RecyclerAdapter.savedquestions.getSavedQuestions());
            notificationIntent = new Intent( context, questionActivity.class );
            System.out.println("The current bundle is  from the push receiver why is it empty:     "+pushNotification.QuestionBundle);
            notificationIntent.putExtras(pushNotification.QuestionBundle);

            notificationIntent.putExtra( "subtopic", subtopic );
            PendingIntent contentIntent = PendingIntent.getActivity( context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT );

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( context );
            notificationBuilder.setSmallIcon( getNotificationIcon() );
            notificationBuilder.setTicker( tickerText );
            notificationBuilder.setWhen( System.currentTimeMillis() );
            notificationBuilder.setContentTitle( contentTitle );
            notificationBuilder.setContentText( contentText );
            notificationBuilder.setAutoCancel( true );
            notificationBuilder.setContentIntent( contentIntent );
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                notificationBuilder.setColor( context.getResources().getColor(R.color.Black));
//            }

            Notification notification = notificationBuilder.build();



            NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
            notificationManager.notify( 0, notification );






        }

        return false;
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.notification_pagk : R.mipmap.ic_launcher;
    }
}
