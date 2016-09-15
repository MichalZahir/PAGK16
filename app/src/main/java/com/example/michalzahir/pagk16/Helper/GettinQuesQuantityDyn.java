package com.example.michalzahir.pagk16.Helper;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.CATEGORY_QUESTIONS.QUESTIONS_SIZE;
import com.example.michalzahir.pagk16.QUESTIONS;

/**
 * Created by zahirm on 2016-09-15.
 */
public class GettinQuesQuantityDyn


{

    public static int GetQuestionsQuantityDynamically(){
        final int[] a = new int[1];


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int d =1;
                    String whereClause = " ID=" + d;
                    BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                    dataQuery.setWhereClause(whereClause);
                    BackendlessCollection<QUESTIONS_SIZE>  qz = Backendless.Persistence.of(QUESTIONS_SIZE.class).find(dataQuery);
                    a[0] = qz.getCurrentPage().get(0).getQuestionsQuantity();
                } catch (BackendlessException e) {
                    System.out.println("It didn't work because :"+e.getCode()+e.getMessage()+e.getDetail());
                }
            }
        });

        t.start(); // spawn thread

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return a[0];
    }
}
