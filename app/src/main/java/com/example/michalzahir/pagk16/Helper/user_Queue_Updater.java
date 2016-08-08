package com.example.michalzahir.pagk16.Helper;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.gameResult;
import com.example.michalzahir.pagk16.gettingQuestions;

import java.util.Arrays;

/**
 * Created by zahirm on 2016-07-04.
 */
public class user_Queue_Updater {
    private static final String TAG = "User queue updater " ;


    static public void saveNewPlayer()
        {

            USERS_QUEUE users_queue = new USERS_QUEUE();
            //users_queue.setDevice_ID("asdasd");
            Log.d(TAG, "Problem  :  " + MainActivity.user.getDeviceID());
            users_queue.setUser_Device_ID(" "+MainActivity.user.getDeviceID());
            Log.d(TAG, "The user's Device ID :  " + MainActivity.user.getDeviceID());
            users_queue.setUser_object_ID(MainActivity.user.getUserObjectId());
            users_queue.setResult(MainActivity.user.getResult());
            users_queue.setUser_Question_Category(MainActivity.user.getCategory());
            //users_queue.setUser_Question_ID(com.example.michalzahir.pagk16.gettingQuestions.QuestionsIDs);
            users_queue.setQuestionIDSArray(Arrays.toString(gettingQuestions.QuestionsIDs));
            users_queue.setUserName(MainActivity.userName.getUserName());

            // save object asynchronously
            Backendless.Persistence.save( users_queue, new AsyncCallback<USERS_QUEUE>() {
                public void handleResponse( USERS_QUEUE response )
                {
                   String UserQueueObjectID =response.getObjectId();
                    Log.d(TAG, "The user was saved successfully to the Queue. That user object id is :  " + UserQueueObjectID);
                }

                public void handleFault( BackendlessFault fault )
                {

                    Log.d(TAG, "Saving the new user in the queue failed because of :" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass() );

                }
            });
        }


}
