package com.example.michalzahir.pagk16;

/**
 * Created by zahirm on 2016-08-08.
 */
public class UserName {
   private String UserName;
   private String oponnentName;
    private String UserNameUSrObjectID;
    private String oponnentUserObjectID;
    public UserName() {
    }

    public UserName(String userName, String oponnentName) {
        UserName = userName;
        this.oponnentName = oponnentName;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getOponnentName() {
        return oponnentName;
    }

    public void setOponnentName(String oponnentName) {
        this.oponnentName = oponnentName;
    }

    public String getUserNameUSrObjectID() {
        return UserNameUSrObjectID;
    }

    public void setUserNameUSrObjectID(String userNameUSrObjectID) {
        UserNameUSrObjectID = userNameUSrObjectID;
    }

    public String getOponnentUserObjectID() {
        return oponnentUserObjectID;
    }

    public void setOponnentUserObjectID(String oponnentUserObjectID) {
        this.oponnentUserObjectID = oponnentUserObjectID;
    }
}
