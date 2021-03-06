package com.example.michalzahir.pagk16.Helper;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.michalzahir.pagk16.MainActivity;
import com.example.michalzahir.pagk16.Profile2_ScrollingActivity;
import com.example.michalzahir.pagk16.RankingActivity;
import com.example.michalzahir.pagk16.UsersDB.Users;
import com.example.michalzahir.pagk16.playerObejtID;

/**
 * Created by zahirm on 2016-07-12.
 */
public class fbUsrStatistics {


    private static final String TAG = "fbUsrStatistics ";
    public static String RankingArrow;

    static public int[] GetFbUsrStatistics(String name) {
        int tab[] = new int[8];

        System.out.println(name);
        MainActivity.userName.setUserName(name);
        String whereClause = " name='" + name + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        try {

            BackendlessCollection<Users> BU = Backendless.Persistence.of(Users.class).find(dataQuery);
            for (Users q : BU.getData()) {
                //Users Response = Backendless.Persistence.of(Users.class).findById(q.getObjectId());

                tab[0] = q.getWON();
                tab[1] = q.getDRAW();
                tab[2] = q.getLOST();
                tab[3] = tab[0] + tab[1] + tab[2];
                tab[4] = q.getRANKING();
                tab[5] = q.getUsersCount();
                tab[6] = q.getPOINTS();
                tab[7] = q.getOLDRANKING();
                RankingArrow = q.getRANKINGARROW();
                String s = q.getObjectId();
                playerObejtID.setUserObjectID(s);
                MainActivity.userName.setUserNameUSrObjectID(s);
                Profile2_ScrollingActivity.AnsweredQuestonsIds = q.getAnsweredQuestionsIDs();
            }
        } catch (BackendlessException fault) {
            Log.d(TAG, "fault trying to get FB users statistics" + fault.getMessage() + fault.getCode() + fault.getDetail() + fault.getClass());

        }


        return tab;
    }
}
