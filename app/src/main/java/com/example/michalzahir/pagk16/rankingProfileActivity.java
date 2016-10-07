package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class rankingProfileActivity extends AppCompatActivity {
    ImageView ProfilPicture;
    Bitmap bitmap = null;
    private TextView UserNameTectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_profile);
        ProfilPicture = (ImageView) findViewById(R.id.ProfilePicRanking);
        UserNameTectView = (TextView) findViewById(R.id.UserNameIconeRanking);
        Intent intent = getIntent();

        final String FacebookID = intent.getStringExtra("FbProfileID");
        String UserName = intent.getStringExtra("UsrsNamesTab");
        UserNameTectView.setText(UserName);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String imageURL;

                imageURL = "https://graph.facebook.com/"+FacebookID+"/picture?type=large";
                InputStream in = null;
                try {
                    in = (InputStream) new URL(imageURL).getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(in);
                ProfilPicture.setImageBitmap(bitmap);

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

                //ProfilPicture.setImageBitmap(new FacebookProfPicture().execute(profile.getId()).get()); //getFacebookProfilePicture(profile.getId()));



    }
}
