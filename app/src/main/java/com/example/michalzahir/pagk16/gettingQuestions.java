package com.example.michalzahir.pagk16;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.Random;

/**
 * Created by zahirm on 2016-05-30.
 */
public class gettingQuestions extends Application {
    private static final String TAG = gettingQuestions.class.getSimpleName();
    private  Bundle  bundle = new Bundle();

    Context context;
    public gettingQuestions(Context context) {
        this.context = context;
    }

    public  Bundle  getQuestions(){

        Random rn = new Random();
        int ID = rn.nextInt(ConstantsClass.QuestionsQuestSize) + 1;
        System.out.println(ID);
        String whereClause = " ID="+ID;
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        //BackendlessCollection<QUESTIONS> result =
        Backendless.Persistence.of( QUESTIONS.class ).find(dataQuery, new AsyncCallback<BackendlessCollection<QUESTIONS>>() {
            @Override
            public void handleResponse(BackendlessCollection<QUESTIONS> foundQuestions){
                for( QUESTIONS q : foundQuestions.getData() )
                {
                    //System.out.println(  " The shit  in the table :  '"+ q.getObjectId()) ;
                    Backendless.Persistence.of( QUESTIONS.class ).findById(q.getObjectId(), new AsyncCallback<QUESTIONS>() {
                        @Override
                        public void handleResponse(QUESTIONS response) {
                            // a Contact instance has been found by ObjectId

                            System.out.println("this is the question from the backendless DB  "+response.getQuestion()
                                   +".    this is the first answer   "+response.getAnswer_a()+".   Hurrraaa success !!!!"+response.getCORRECT_A()+" B boolean:"+response.getCORRECT_B()+" D boolean:"+response.getCORRECT_D()+" C boolean:"+response.getCORRECT_C()+"AA"+response.getAnswer_a()+"bA"+response.getANSWER_B()+"cA"+response.getANSWER_C()+"DA"+response.getANSWER_D());
                            bundle.putString("Question",response.getQuestion());
                            bundle.putString("Answer_A",response.getAnswer_a());
                            bundle.putString("Answer_B",response.getANSWER_B());
                            bundle.putString("Answer_C",response.getANSWER_C());
                            bundle.putString("Answer_D",response.getANSWER_D());
                            bundle.putBoolean("correct_A",response.getCORRECT_A());
                            bundle.putBoolean("correct_B",response.getCORRECT_B());
                            bundle.putBoolean("correct_D",response.getCORRECT_C());
                            bundle.putBoolean("correct_C",response.getCORRECT_D());

                            Log.d(TAG, "trying to fetch questions from DB" +bundle);
                            //StartActivity(bundle ,context);

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());
                        }
                    });
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                            Log.d(TAG, "fault trying to fetch questions from DB fault" + fault.getMessage()+fault.getCode()+fault.getDetail()+fault.getClass());

            }});
        return bundle;

    }

}
