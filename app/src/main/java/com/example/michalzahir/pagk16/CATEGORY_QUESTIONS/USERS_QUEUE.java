package com.example.michalzahir.pagk16.CATEGORY_QUESTIONS;

import java.util.Date;

/**
 * Created by zahirm on 2016-06-30.
 */
public class USERS_QUEUE {
    private String objectId;
    private Date created;
    private Date updated;
    int Question_ID;



    String Question_Category;
    int Result;
    String Device_ID;
    String user_object_ID;

    public USERS_QUEUE() {
    }

    public USERS_QUEUE(String objectId, Date created, Date updated, int question_ID, String question_Category, int result, String device_ID, String user_object_ID) {
        this.objectId = objectId;
        this.created = created;
        this.updated = updated;
        Question_ID = question_ID;
        Question_Category = question_Category;
        Result = result;
        Device_ID = device_ID;
        this.user_object_ID = user_object_ID;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getDevice_ID() {
        return Device_ID;
    }

    public void setDevice_ID(String device_ID) {
        Device_ID = device_ID;
    }

    public String getUser_object_ID() {
        return user_object_ID;
    }

    public void setUser_object_ID(String user_object_ID) {
        this.user_object_ID = user_object_ID;
    }






    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getID() {
        return Question_ID;
    }

    public void setID(int Question_ID) {
        this.Question_ID = Question_ID;
    }

    public String getCATEGORY() {
        return Question_Category;
    }

    public void setCATEGORY(String Question_Category) {
        this.Question_Category = Question_Category;
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
