package com.example.michalzahir.pagk16.SavingMyAnsweredQuestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mike on 9/10/2016.
 */
public class RetrievingQuesIDs {
    public static Set< Integer> RetrieveAnsQuesIDS(String AnsweredQuestionsIDs){
        //String helper = AnsweredQuestionsIDs.substring(1,AnsweredQuestionsIDs.length()-1);
        String s =AnsweredQuestionsIDs.replaceAll("\\[", "").replaceAll("\\]",",");

        HashMap <Integer,Integer> AnsweredQuestions = new HashMap<>();
        Set<Integer> testSet = new HashSet<Integer>();
        String[] strArray = s.split(",");

        for(int i = 0; i < strArray.length; i++) {
            strArray[i] = strArray[i].replaceAll(" ", "");
            //AnsweredQuestions.put(i, Integer.valueOf(strArray[i]));
            testSet.add(Integer.valueOf(strArray[i]));
         }

        return testSet;
    }
}
