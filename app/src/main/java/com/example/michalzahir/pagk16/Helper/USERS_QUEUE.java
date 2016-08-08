package com.example.michalzahir.pagk16.Helper;

import java.util.Date;

/**
 * Created by zahirm on 2016-07-04.
 */
public class USERS_QUEUE {

    private String objectId;
    private Date created;
    private Date updated;
    private String User_Device_ID;
    private String User_Question_Category;
    private int User_Question_ID;
    private int Result;
    private String user_object_ID;
    private String QuestionIDSArray;
    private String UserName;

    public USERS_QUEUE() {
    }

    public USERS_QUEUE(String objectId, Date created, Date updated, String user_Device_ID, String user_Question_Category,
                       int user_Question_ID, int result, String user_object_ID, String questionIDSArray, String userName) {
        this.objectId = objectId;
        this.created = created;
        this.updated = updated;
        User_Device_ID = user_Device_ID;
        User_Question_Category = user_Question_Category;
        User_Question_ID = user_Question_ID;
        Result = result;
        this.user_object_ID = user_object_ID;
        QuestionIDSArray = questionIDSArray;
        UserName = userName;
    }

    public String getQuestionIDSArray() {
        return QuestionIDSArray;
    }

    public void setQuestionIDSArray(String questionIDSArray) {
        QuestionIDSArray = questionIDSArray;
    }

    public String getUser_Device_ID() {
        return User_Device_ID;
    }

    public void setUser_Device_ID(String user_Device_ID) {
        User_Device_ID = user_Device_ID;
    }

    public String getUser_Question_Category() {
        return User_Question_Category;
    }

    public void setUser_Question_Category(String user_Question_Category) {
        User_Question_Category = user_Question_Category;
    }

    public int getUser_Question_ID() {
        return User_Question_ID;
    }

    public void setUser_Question_ID(int user_Question_ID) {
        User_Question_ID = user_Question_ID;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        this.Result = result;
    }

    public String getUser_object_ID() {
        return user_object_ID;
    }

    public void setUser_object_ID(String user_object_ID) {
        this.user_object_ID = user_object_ID;
    }

    public String getObjectId()
    {
        return objectId;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
