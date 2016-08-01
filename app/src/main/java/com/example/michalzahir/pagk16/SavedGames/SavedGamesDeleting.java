package com.example.michalzahir.pagk16.SavedGames;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.michalzahir.pagk16.Helper.USERS_QUEUE;

/**
 * Created by zahirm on 2016-08-01.
 */
public class SavedGamesDeleting {


    private static final String TAG = "Deleting saved games";

    public static void DeleteSavedGame(Saved_Games saved_games ) {

        Backendless.Persistence.of( Saved_Games.class ).remove( saved_games, new AsyncCallback<Long>()
        {


            public void handleResponse(Long response )
            {
                // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                Log.d(TAG, "  Success. deleting the saved game successfully:" +response);
            }
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d(TAG, "Error while deleting the saved game successfully" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());
            }
        } );

    }
    }

