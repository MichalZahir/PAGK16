package com.example.michalzahir.pagk16.ServiceAppOff;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zahirm on 2016-07-20.
 */
public abstract class AbstractService extends Service {

    protected final String TAG = this.getClass().getName();

    @Override
    public void onCreate() {
        super.onCreate();
        onStartService();
        Log.i(TAG, "onCreate(): Service Started.");
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStarCommand(): Received id " + startId + ": " + intent);
        return START_STICKY; // run until explicitly stopped.
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        onStopService();
        Log.i(TAG, "Service Stopped.");
    }

    public abstract void onStartService();
    public abstract void onStopService();

    @Override
    public void onTaskRemoved(Intent rootIntent) {
       // Toast.makeText(getApplicationContext(), "AS onTaskRemoved called", Toast.LENGTH_LONG).show();
        super.onTaskRemoved(rootIntent);
    }
}