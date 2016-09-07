package com.example.michalzahir.pagk16.FacebookUsers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.R;
import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import bolts.AppLinks;

public class invitingFriendsActivity extends AppCompatActivity {
    private Button backToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inviting_friends);
        backToProfile = (Button) findViewById(R.id.backToProfileButton);


        // app link url
        FacebookSdk.sdkInitialize(getApplicationContext());
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        }



        //app link url
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://fb.me/1718891101713602";
        previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";


        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }


        backToProfile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent( invitingFriendsActivity.this, Profile2_ScrollingActivity.class);
                startActivity(i);
                finish();
            }

        });



    }
}
