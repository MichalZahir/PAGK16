package com.example.michalzahir.pagk16;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zahirm on 2016-06-02.
 */
public class SavedQuestions {

     static List<QUESTIONS> savedQuestions = new ArrayList<>();

    public SavedQuestions(List<QUESTIONS> savedQuestions) {
        this.savedQuestions = savedQuestions;
    }

    public List<QUESTIONS> getSavedQuestions() {
        return savedQuestions;

    }

    public void setSavedQuestions(List<QUESTIONS> savedQuestions) {
        this.savedQuestions = savedQuestions;
    }

    public static void ClearSavedQuestions(){

        savedQuestions.clear();
    }
    public void addToSavedQuestions(QUESTIONS question){
       savedQuestions.add(question);

    }
    public static  void ListToBundleStartQueAct( Context context){


        Bundle QuestionBundle = new Bundle();

        for (int i = 0 ; i< savedQuestions.size();i++) {
            System.out.println("this is the question from the backendless DB  " + savedQuestions.get(i).getQuestion()
                    + ".    this is the first answer   " + savedQuestions.get(i).getAnswer_a() + ".   Hurrraaa success !!!!" + savedQuestions.get(i).getCORRECT_A() + " B boolean:" + savedQuestions.get(i).getCORRECT_B() + " D boolean:" + savedQuestions.get(i).getCORRECT_D() + " C boolean:" + savedQuestions.get(i).getCORRECT_C() + "AA" + savedQuestions.get(i).getAnswer_a() + "bA" + savedQuestions.get(i).getANSWER_B() + "cA" + savedQuestions.get(i).getANSWER_C() + "DA" + savedQuestions.get(i).getANSWER_D());
            QuestionBundle.putString("Question", savedQuestions.get(i).getQuestion());
            QuestionBundle.putString("Answer_A", savedQuestions.get(i).getAnswer_a());
            QuestionBundle.putString("Answer_B", savedQuestions.get(i).getANSWER_B());
            QuestionBundle.putString("Answer_C", savedQuestions.get(i).getANSWER_C());
            QuestionBundle.putString("Answer_D", savedQuestions.get(i).getANSWER_D());
            QuestionBundle.putBoolean("correct_A", savedQuestions.get(i).getCORRECT_A());
            QuestionBundle.putBoolean("correct_B", savedQuestions.get(i).getCORRECT_B());
            QuestionBundle.putBoolean("correct_C", savedQuestions.get(i).getCORRECT_C());
            QuestionBundle.putBoolean("correct_D", savedQuestions.get(i).getCORRECT_D());
            Intent in = new Intent(context, questionActivity.class);
            in.putExtras(QuestionBundle);
            context.startActivity(in);

        }
        savedQuestions.clear();
    }

}
