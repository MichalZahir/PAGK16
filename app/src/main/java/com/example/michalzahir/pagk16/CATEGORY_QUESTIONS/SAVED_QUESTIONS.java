package com.example.michalzahir.pagk16.CATEGORY_QUESTIONS;

import java.util.Date;

/**
 * Created by zahirm on 2016-06-21.
 */
public class SAVED_QUESTIONS {
    private String objectId;
    private Date created;
    private Date updated;
    int ID;
    String CATEGORY;


    public SAVED_QUESTIONS(String objectId, Date created, Date updated, int ID, String CATEGORY) {
        this.objectId = objectId;
        this.created = created;
        this.updated = updated;
        this.ID = ID;
        this.CATEGORY = CATEGORY;
    }

    public SAVED_QUESTIONS() {
    }



    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
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

}
