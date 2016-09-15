package com.example.michalzahir.pagk16.CATEGORY_QUESTIONS;

import java.util.Date;

/**
 * Created by zahirm on 2016-09-15.
 */
public class QUESTIONS_SIZE {

    private String objectId;
    private Date created;
    private Date updated;
    private int ID;
    private int QuestionsQuantity;

    public QUESTIONS_SIZE() {
    }

    public QUESTIONS_SIZE(String objectId, int questionsQuantity, int ID, Date updated, Date created) {
        this.objectId = objectId;
        QuestionsQuantity = questionsQuantity;
        this.ID = ID;
        this.updated = updated;
        this.created = created;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQuestionsQuantity() {
        return QuestionsQuantity;
    }

    public void setQuestionsQuantity(int questionsQuantity) {
        QuestionsQuantity = questionsQuantity;
    }
}
