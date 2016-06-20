package com.example.michalzahir.pagk16.CATEGORY_QUESTIONS;

import com.example.michalzahir.pagk16.QUESTIONS;

import java.util.Date;

/**
 * Created by zahirm on 2016-06-20.
 */
public class GEOGRAPHY_QUESTIONS extends QUESTIONS {
    private String objectId;
    private int ID;
    private Date created;
    private Date updated;
    private String QUESTION;
    private String ANSWER_A;
    private String ANSWER_B;
    private String ANSWER_C;



    private String ANSWER_D;
    private Boolean CORRECT_A;
    private Boolean CORRECT_B;
    private Boolean CORRECT_C;
    private Boolean CORRECT_D;

    public GEOGRAPHY_QUESTIONS(String objectId, int ID, Date created, Date updated, String ANSWER_A, String QUESTION,
                               String ANSWER_B, String ANSWER_C, String ANSWER_D, Boolean CORRECT_A,
                               Boolean CORRECT_B, Boolean CORRECT_C, Boolean CORRECT_D) {
        this.objectId = objectId;
        this.created = created;
        this.updated = updated;
        this.ANSWER_A = ANSWER_A;
        this.QUESTION = QUESTION;
        this.ANSWER_B = ANSWER_B;
        this.ANSWER_C = ANSWER_C;
        this.ANSWER_D = ANSWER_D;
        this.CORRECT_A = CORRECT_A;
        this.CORRECT_B = CORRECT_B;
        this.CORRECT_C = CORRECT_C;
        this.CORRECT_D = CORRECT_D;
        this.ID = ID;
    }

    public GEOGRAPHY_QUESTIONS() {

    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Boolean getCORRECT_A() {
        return CORRECT_A;
    }

    public void setCORRECT_A(Boolean CORRECT_A) {
        this.CORRECT_A = CORRECT_A;
    }

    public Boolean getCORRECT_B() {
        return CORRECT_B;
    }

    public void setCORRECT_B(Boolean CORRECT_B) {
        this.CORRECT_B = CORRECT_B;
    }

    public Boolean getCORRECT_D() {
        return CORRECT_D;
    }

    public void setCORRECT_D(Boolean CORRECT_D) {
        this.CORRECT_D = CORRECT_D;
    }

    public Boolean getCORRECT_C() {
        return CORRECT_C;
    }

    public void setCORRECT_C(Boolean CORRECT_C) {
        this.CORRECT_C = CORRECT_C;
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

    public String getANSWER_B() {
        return ANSWER_B;
    }

    public void setANSWER_B(String ANSWER_B) {
        this.ANSWER_B = ANSWER_B;
    }

    public String getQuestion() {
        return QUESTION;
    }

    public void setQuestion(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public String getANSWER_C() {
        return ANSWER_C;
    }

    public void setANSWER_C(String ANSWER_C) {
        this.ANSWER_C = ANSWER_C;
    }

    public String getANSWER_D() {
        return ANSWER_D;
    }

    public void setANSWER_D(String ANSWER_D) {
        this.ANSWER_D = ANSWER_D;
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
