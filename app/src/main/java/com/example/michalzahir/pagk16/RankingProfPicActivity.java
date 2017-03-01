package com.example.michalzahir.pagk16;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class RankingProfPicActivity extends AppCompatActivity {
    ImageView ProfilPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_prof_pic);
        ProfilPicture = (ImageView) findViewById(R.id.profileImageView);
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("ProfilePicture");
        ProfilPicture.setImageBitmap(bitmap);
    }
}
