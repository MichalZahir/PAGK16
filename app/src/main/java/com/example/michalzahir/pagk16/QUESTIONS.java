package com.example.michalzahir.pagk16;

import java.util.Date;

/**
 * Created by michal.zahir on 2016-04-16.
 */
public class QUESTIONS {
    private String objectId;
    private int ID;
    private Date created;
    private Date updated;
    private String QUESTION;
    private String ANSWER_A;
    private String answer_b;
    private String answer_c;



    private String answer_d;
    private Boolean correct_a;
    private Boolean correct_b;
    private Boolean correct_c;
    private Boolean correct_d;

    public QUESTIONS(String objectId, int ID, Date created, Date updated, String ANSWER_A, String QUESTION,
                     String answer_b, String answer_c, String answer_d, Boolean correct_a,
                     Boolean correct_b,Boolean correct_c, Boolean correct_d) {
        this.objectId = objectId;
        this.created = created;
        this.updated = updated;
        this.ANSWER_A = ANSWER_A;
        this.QUESTION = QUESTION;
        this.answer_b = answer_b;
        this.answer_c = answer_c;
        this.answer_d = answer_d;
        this.correct_a = correct_a;
        this.correct_b = correct_b;
        this.correct_c = correct_c;
        this.correct_d = correct_d;
        this.ID = ID;
    }

    public QUESTIONS() {

    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Boolean getCorrect_a() {
        return correct_a;
    }

    public void setCorrect_a(Boolean correct_a) {
        this.correct_a = correct_a;
    }

    public Boolean getCorrect_b() {
        return correct_b;
    }

    public void setCorrect_b(Boolean correct_b) {
        this.correct_b = correct_b;
    }

    public Boolean getCorrect_d() {
        return correct_d;
    }

    public void setCorrect_d(Boolean correct_d) {
        this.correct_d = correct_d;
    }

    public Boolean getCorrect_c() {
        return correct_c;
    }

    public void setCorrect_c(Boolean correct_c) {
        this.correct_c = correct_c;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public String getAnswer_a() {
        return ANSWER_A;
    }

    public void setAnswer_a(String ANSWER_A) {
        this.ANSWER_A = ANSWER_A;
    }

    public String getAnswer_b() {
        return answer_b;
    }

    public void setAnswer_b(String answer_b) {
        this.answer_b = answer_b;
    }

    public String getQuestion() {
        return QUESTION;
    }

    public void setQuestion(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public String getAnswer_c() {
        return answer_c;
    }

    public void setAnswer_c(String answer_c) {
        this.answer_c = answer_c;
    }

    public String getAnswer_d() {
        return answer_d;
    }

    public void setAnswer_d(String answer_d) {
        this.answer_d = answer_d;
    }

    public void setObjectId( String objectId )
    {
        this.objectId = objectId;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated( Date created )
    {
        this.created = created;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public void setUpdated( Date updated )
    {
        this.updated = updated;
    }

}
