package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.backendless.Backendless;
import com.facebook.FacebookSdk;

/**
 * Created by zahirm on 2016-06-02.
 */
public class playerObejtID {

    public static String UserObjectID;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String getUserObjectID() {
        return UserObjectID;
    }

    public static void setUserObjectID(String userObjectID) {
        UserObjectID = userObjectID;
    }
    public static void SaveUserObjectIDOnDestroy( Context c){

        SharedPreferences preferences = c.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString("playerObjectID", playerObejtID.getUserObjectID());
        editor.apply();
    }
    public static void SetUserObjectIDOnStart( Context c){
        final String appVersion = "v1";
        Backendless.initApp(c, "49D5B4BA-6BE5-9529-FF74-3DA2B56A3C00", "836D3D29-DD33-A22B-FFF5-E2DA720F6700", appVersion);
        FacebookSdk.sdkInitialize(c);
        SharedPreferences prefs =  c.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (prefs.contains("playerObjectID")) {
            String userObjID = prefs.getString("playerObjectID", "000");


            playerObejtID.setUserObjectID(userObjID);
        }
    }


}
