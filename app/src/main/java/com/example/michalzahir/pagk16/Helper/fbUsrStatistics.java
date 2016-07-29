package com.example.michalzahir.pagk16.Helper;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.UsersDB.Users;

/**
 * Created by zahirm on 2016-07-12.
 */
public class fbUsrStatistics {


    private static final String TAG = "fbUsrStatistics ";

    static public int [ ] GetFbUsrStatistics(String name){
        int tab [] =  new int [4];
        String userObjectID = null;
        //final String[] UserObjcetID = new String[1];
        System.out.println(name);
        String whereClause = " name='" + name+"'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        try {
            BackendlessCollection<Users> BU = Backendless.Persistence.of(Users.class).find(dataQuery);
            for (Users q : BU.getData()) {
                //Users Response = Backendless.Persistence.of(Users.class).findById(q.getObjectId());
                userObjectID = q.getObjectId();
                tab[0] = q.getWON();
                tab[1] = q.getDRAW();
                tab[2] = q.getLOST();
                tab[3] = tab[0] + tab[1] + tab[2];

            }
        }
        catch (BackendlessException fault){
            Log.d(TAG, "fault trying to get FB users statistics" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

        }





    return tab;
    }
}
