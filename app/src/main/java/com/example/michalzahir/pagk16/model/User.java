package com.example.michalzahir.pagk16.model;

/**
 * Created by zahirm on 2016-06-30.
 */
public class User {
//

    String userObjectId;
    String DeviceID;
    String Name;


    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
