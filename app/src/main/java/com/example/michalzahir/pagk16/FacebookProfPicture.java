package com.example.michalzahir.pagk16;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mike on 22.05.16.
 */
public class FacebookProfPicture extends AsyncTask<String,Integer, Bitmap> {
    public static Bitmap getFacebookProfilePicture (String userID) {

            URL imageURL = null;
            try {
                imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = null;
            Bitmap  bitmapScaled=null;
            try {

                bitmap = BitmapFactory.decodeStream( imageURL.openConnection().getInputStream());
                 bitmapScaled = Bitmap.createScaledBitmap(bitmap, 950, 534, true);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmapScaled;
        }

    @Override
    protected Bitmap doInBackground(String... params) {
        return getFacebookProfilePicture(params[0]);
    }
    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
}
