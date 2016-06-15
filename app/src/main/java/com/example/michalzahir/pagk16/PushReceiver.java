package com.example.michalzahir.pagk16;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.backendless.messaging.PublishOptions;
import com.backendless.push.BackendlessBroadcastReceiver;
import com.example.michalzahir.pagk16.adapter.RecyclerAdapter;

import java.util.List;

public class PushReceiver extends BackendlessBroadcastReceiver
{

    Bundle QuestionBundle = new Bundle();

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

                           SavedQuestionsToBundle(RecyclerAdapter.savedquestions.getSavedQuestions());
            Intent notificationIntent = new Intent( context, questionActivity.class );
            notificationIntent.putExtras(QuestionBundle);

            notificationIntent.putExtra( "subtopic", subtopic );
            PendingIntent contentIntent = PendingIntent.getActivity( context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT );

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( context );
            notificationBuilder.setSmallIcon( appIcon );
            notificationBuilder.setTicker( tickerText );
            notificationBuilder.setWhen( System.currentTimeMillis() );
            notificationBuilder.setContentTitle( contentTitle );
            notificationBuilder.setContentText( contentText );
            notificationBuilder.setAutoCancel( true );
            notificationBuilder.setContentIntent( contentIntent );

            Notification notification = notificationBuilder.build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
            notificationManager.notify( 0, notification );
        }

        return false;
    }
    public void SavedQuestionsToBundle(List<QUESTIONS> savedQuestions){



        for (int i = 0 ; i< savedQuestions.size();i++) {
            System.out.println("this is the question from the backendless DB  " + savedQuestions.get(i).getQuestion()
                    + ".    this is the first answer   " + savedQuestions.get(i).getAnswer_a() + ".   Hurrraaa success !!!!" + savedQuestions.get(i).getCORRECT_A() + " B boolean:" + savedQuestions.get(i).getCORRECT_B() + " D boolean:" + savedQuestions.get(i).getCORRECT_D() + " C boolean:" + savedQuestions.get(i).getCORRECT_C() + "AA" + savedQuestions.get(i).getAnswer_a() + "bA" + savedQuestions.get(i).getANSWER_B() + "cA" + savedQuestions.get(i).getANSWER_C() + "DA" + savedQuestions.get(i).getANSWER_D());
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
