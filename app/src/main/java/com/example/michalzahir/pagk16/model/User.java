package com.example.michalzahir.pagk16.model;

/**
 * Created by zahirm on 2016-06-30.
 */
public class User {
//

    String userObjectId;
    String DeviceID;
    String Name;
    String Category;
    int Question_ID;
    int result;

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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getQuestion_ID() {
        return Question_ID;
    }

    public void setQuestion_ID(int question_ID) {
        Question_ID = question_ID;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
