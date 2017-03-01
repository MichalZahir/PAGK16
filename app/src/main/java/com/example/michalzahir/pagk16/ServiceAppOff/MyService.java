package com.example.michalzahir.pagk16.ServiceAppOff;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.michalzahir.pagk16.R;

/**
 * Created by zahirm on 2016-07-20.
 */
public class MyService extends AbstractService {

    @Override
    public void onStartService() {


    }

    @Override
    public void onStopService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Toast.makeText(getApplicationContext(), "Your game was saved", Toast.LENGTH_LONG).show();
        System.out.println("onTaskRemoved called");
        com.example.michalzahir.pagk16.SavedGames.GamesSaving.SaveGame(this.getClass().getSimpleName());

        super.onTaskRemoved(rootIntent);
    }
}