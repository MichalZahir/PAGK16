package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zahirm on 2016-06-02.
 */
public class gameResult {


    int firstUserResult;
    int secondtUserResult;
    String firstUSerObjectID;
    String secondUSerObjectID;

    public gameResult(int firstUserResult, int secondtUserResult, String firstUSerObjectID, String secondUSerObjectID) {
        this.firstUserResult = firstUserResult;
        this.secondtUserResult = secondtUserResult;
        this.firstUSerObjectID = firstUSerObjectID;
        this.secondUSerObjectID = secondUSerObjectID;
    }

    public gameResult() {
    }

    public int getSecondtUserResult() {
        return secondtUserResult;
    }

    public void setSecondtUserResult(int secondtUserResult) {
        this.secondtUserResult = secondtUserResult;
    }

    public int getFirstUserResult() {
        return firstUserResult;
    }

    public void setFirstUserResult(int firstUserResult) {
        this.firstUserResult = firstUserResult;
    }

    public String getFirstUSerObjectID() {
        return firstUSerObjectID;
    }

    public void setFirstUSerObjectID(String firstUSerObjectID) {
        this.firstUSerObjectID = firstUSerObjectID;
    }

    public String getSecondUSerObjectID() {
        return secondUSerObjectID;
    }

    public void setSecondUSerObjectID(String secondUSerObjectID) {
        this.secondUSerObjectID = secondUSerObjectID;
    }
    public void Increment1stUserResult(){

        this.firstUserResult=this.firstUserResult+1;
    }
    public void Increment2ndUserResult(){

        this.secondtUserResult=this.secondtUserResult+1;
    }
    public void publishResults(Context context){
        Bundle resultsBundle = new Bundle();
        resultsBundle.putInt("1st user result",getFirstUserResult());
        resultsBundle.putInt("2nd user result",getSecondtUserResult());
        Intent i = new Intent(context, resultActivity.class);
        i.putExtras(resultsBundle);
        context.startActivity(i);
    }
}
