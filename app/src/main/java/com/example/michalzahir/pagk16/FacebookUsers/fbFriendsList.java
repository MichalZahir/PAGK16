package com.example.michalzahir.pagk16.FacebookUsers;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

/**
 * Created by zahirm on 2016-07-07.
 */
public class fbFriendsList {



    public static void getFrienList(){

       /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                       System.out.println(response.getRawResponse() + "Error " + response.getError() +"The JSON Array " +response.getJSONArray());
                        System.out.println("Response to String : " + response.toString());
                    }
                }
        ).executeAsync();



    }
}
