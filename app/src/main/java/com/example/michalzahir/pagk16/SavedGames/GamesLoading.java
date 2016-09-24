package com.example.michalzahir.pagk16.SavedGames;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.michalzahir.pagk16.ConstantsClass;
import com.example.michalzahir.pagk16.QUESTIONS;
import com.example.michalzahir.pagk16.playerObejtID;
import com.example.michalzahir.pagk16.questionActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zahirm on 2016-07-27.
 */
public class GamesLoading {

    private static final String TAG = "Saved Games Loading";
    static ArrayList<Saved_Games> SavedGameslist = new ArrayList<Saved_Games>();
    static  void loadSavedGames(){
        SavedGameslist.clear();
        Log.d(TAG, "player object ID b4 the query " + playerObejtID.getUserObjectID());
        String whereClause = "firstUserID='"+ playerObejtID.getUserObjectID()+"'"+"OR secondUserID='"+ playerObejtID.getUserObjectID()+"'";
        final BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        dataQuery.setQueryOptions(queryOptions);
        dataQuery.setWhereClause(whereClause);
        final BackendlessCollection<Saved_Games>[] foundGames = new BackendlessCollection[]{null};

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    try {
                        foundGames[0] = Backendless.Persistence.of( Saved_Games.class ).find(dataQuery);
                    } catch (BackendlessException e) {
                        Log.d(TAG, "fault trying to fetch The saved Games  from DB fault" + e.getMessage()+e.getCode()+e.getDetail()+e.getClass());
                        e.printStackTrace();
                    }

                    try {
                        for( Saved_Games q : foundGames[0].getData() )
                        {
                           // Saved_Games response = Backendless.Persistence.of( Saved_Games.class ).findById(q.getObjectId());
                            Log.d(TAG, "The saved Games were Found  First user ID  " +q.getFirstUserID()+" SecondUserID" +q.getSecondUserID() + " Saved Games Object ID:" +q.getObjectId());
                            SavedGameslist.add(q);
                        }
                    } catch (BackendlessException e) {
                        e.printStackTrace();
                        Log.d(TAG, "fault trying to fetch The saved Games  from DB fault" + e.getMessage()+e.getCode()+e.getDetail()+e.getClass());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        Backendless.Persistence.of( Saved_Games.class ).find(dataQuery, new AsyncCallback<BackendlessCollection<Saved_Games>>() {
//            @Override
//            public void handleResponse(BackendlessCollection<Saved_Games> foundGames){
//                for( Saved_Games q : foundGames.getData() )
//                {
//                     Backendless.Persistence.of( Saved_Games.class ).findById(q.getObjectId(), new AsyncCallback<Saved_Games>() {
//                        @Override
//                        public void handleResponse(Saved_Games response) {
//                            SavedGameslist.add(response);
//                            Log.d(TAG, "The saved Games were Found  First user ID  " +response.getFirstUserID()+" SecondUserID" +response.getSecondUserID() + " Saved Games Object ID:" +response.getObjectId());
//
//                        }
//
//                        @Override
//                        public void handleFault(BackendlessFault fault) {
//                            Log.d(TAG, "fault trying to fetch The saved Games  from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Log.d(TAG, "fault trying to fetch saved Games from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());
//
//            }});



    }
}
