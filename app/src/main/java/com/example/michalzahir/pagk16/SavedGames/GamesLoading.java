package com.example.michalzahir.pagk16.SavedGames;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.ConstantsClass;
import com.example.michalzahir.pagk16.QUESTIONS;
import com.example.michalzahir.pagk16.playerObejtID;
import com.example.michalzahir.pagk16.questionActivity;

import java.util.Random;

/**
 * Created by zahirm on 2016-07-27.
 */
public class GamesLoading {

    private static final String TAG = "Saved Games Loading";

    static  void loadSavedGames(){


        String whereClause = "firstUserID='"+ playerObejtID.getUserObjectID()+"'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of( Saved_Games.class ).find(dataQuery, new AsyncCallback<BackendlessCollection<Saved_Games>>() {
            @Override
            public void handleResponse(BackendlessCollection<Saved_Games> foundGames){
                for( Saved_Games q : foundGames.getData() )
                {
                     Backendless.Persistence.of( Saved_Games.class ).findById(q.getObjectId(), new AsyncCallback<Saved_Games>() {
                        @Override
                        public void handleResponse(Saved_Games response) {

                            Log.d(TAG, "The saved Games were Found  First user ID  " +response.getFirstUserID()+" SecondUserID" +response.getSecondUserID() + " Saved Games Object ID:" +response.getObjectId());

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "fault trying to fetch The saved Games  from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());
                        }
                    });
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "fault trying to fetch saved Games from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());

            }});



    }
}
