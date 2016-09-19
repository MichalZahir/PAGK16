package com.example.michalzahir.pagk16;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by zahirm on 2016-06-01.
 */
public class UserUpdatePushNotif {
    public BackendlessUser getBackendlessUserr() {
        return backendlessUserr;
    }

    public void setBackendlessUserr(BackendlessUser backendlessUserr) {
        this.backendlessUserr = backendlessUserr;
    }

    private BackendlessUser backendlessUserr;
    private static final String TAG = UserUpdatePushNotif.class.getSimpleName();

    public static void UpdateUserWithDeviceID(final String Device_ID) {

        final String currentUserObjectId = playerObejtID.getUserObjectID();
        System.out.println("the current user for fb users :    " + currentUserObjectId);


        Backendless.UserService.findById  (currentUserObjectId, new AsyncCallback<BackendlessUser>() { @Override
        public void handleResponse(BackendlessUser backendlessUser )
        {
            System.out.println(backendlessUser.getObjectId());
            backendlessUser.setProperty("Device_ID", Device_ID);
            MainActivity.userName.setUserNameUSrObjectID(currentUserObjectId);
            MainActivity.userName.setUserName((String) backendlessUser.getProperty("name"));
            Backendless.UserService.update(backendlessUser, new AsyncCallback<BackendlessUser>() {
                public void handleResponse(BackendlessUser user) {
                    user.setProperty("Device_ID", Device_ID);

                    Log.d(TAG, "The Device ID is updated successfully for the user  :" + user.getUserId());
                }

                public void handleFault(BackendlessFault fault) {
                    Log.d(TAG, "User not updated (Device ID Update ) for the reasons" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

                }
            });


        }

            @Override
            public void handleFault( BackendlessFault fault )
            {
                System.err.println( "Error - " + fault );
            }});




    }


    }


