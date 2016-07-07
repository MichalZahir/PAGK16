package com.example.michalzahir.pagk16.FacebookUsers;

import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by zahirm on 2016-07-07.
 */
public class fbFriendsList {



    public static void getFriendList(final Context c ){

       /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        Intent intent = new Intent(c,fbFriendsListActivity.class);
                        try {
                            JSONArray rawName = response.getJSONObject().getJSONArray("data");
                            intent.putExtra("jsondata", rawName.toString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            c.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();



    }
}
