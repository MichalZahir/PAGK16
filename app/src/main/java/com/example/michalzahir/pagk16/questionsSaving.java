package com.example.michalzahir.pagk16;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zahirm on 2016-06-20.
 */
public class questionsSaving extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_TABLE_NAME = "saved_questions";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    "QUESTION" + " TEXT, " +"ANSWER_A" + " TEXT, " +"ANSWER_B" + " TEXT, " +"ANSWER_C" + " TEXT, " +"ANSWER_D" + " TEXT, " +"ID" + " INT, " +"CORRECT_A" + " INT, " +"CORRECT_B" + " INT, " +"CORRECT_C" + " INT, " +
                    "CORRECT_D" + " INT);";
    private static final String DATABASE_NAME = "QUESTIONS";

    questionsSaving(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}