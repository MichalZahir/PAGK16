package com.example.michalzahir.pagk16.SavingMyAnsweredQuestions;

import java.util.HashMap;

/**
 * Created by Mike on 9/10/2016.
 */
public class RetrievingQuesIDs {
    public HashMap<Integer,Integer> RetrieveAnsQuesIDS(String AnsweredQuestionsIDs){
        String helper = AnsweredQuestionsIDs.substring(1,AnsweredQuestionsIDs.length()-1);
        helper.replaceAll("\\[\\]","");

        HashMap <Integer,Integer> AnsweredQuestions = new HashMap<>();

        String[] strArray = helper.split(",");

        for(int i = 0; i < strArray.length; i++) {
            strArray[i] = strArray[i].replaceAll(" ", "");
            AnsweredQuestions.put(i, Integer.valueOf(strArray[i]));
         }

        return AnsweredQuestions;
    }
}
