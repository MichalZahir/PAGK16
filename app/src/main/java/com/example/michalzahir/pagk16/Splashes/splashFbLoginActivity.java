package com.example.michalzahir.pagk16.Splashes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.R;

public class splashFbLoginActivity extends Activity {
    private static int SPLASH_TIME_OUT =  0;
    static public splashFbLoginActivity splash;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_fb_login);
        final Intent intent = getIntent();
        System.out.println("I'm in the splash screen activity.");
        splash =this;


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(),
                        Profile2_ScrollingActivity.class);

                i.putExtras(intent.getExtras());
                i.putExtra("caller", "splashFbLoginActivity");
                startActivityForResult(i, 1);
                //finish();

            }
        }, SPLASH_TIME_OUT);

    }
}
