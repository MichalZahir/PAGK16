package com.example.michalzahir.pagk16.Helper;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by zahirm on 2016-07-05.
 */
public class user_Queue_Deleter {

    public static final String TAG = " user_Queue_Deleter" ;
    static public void DeleteOponent(USERS_QUEUE users_queue){
        Backendless.Persistence.of( USERS_QUEUE.class ).remove( users_queue, new AsyncCallback<Long>()
        {


            public void handleResponse(Long response )
            {
                // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                Log.d(TAG, "  Success. deleting the opponent after retrieving it from the DB ended up successfully : the object was deleted at this time:   " +response);
            }
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(TAG, "Error while deleting the opponent after retrieving it from the DB :   " + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
            }
        } );



    }
}
