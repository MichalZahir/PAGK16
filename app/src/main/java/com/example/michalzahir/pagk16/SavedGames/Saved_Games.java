package com.example.michalzahir.pagk16.SavedGames;

import java.util.Date;

/**
 * Created by zahirm on 2016-07-18.
 */
public class Saved_Games {

    private String objectId;
    private int ID;
    private Date created;
    private Date updated;
    private String firstUserID;
    private int firstUserResult;
    private String secondUserID;
    private int secondUserResult;
    private int stopTheGame;
    private Boolean fbGame;
    private String firstUserName;
    private String secondUserName;
    private String firstUserDeviceID;
    private String secondUserDeviceID;
    private String Activity;
    private String WhosTurn;

    public Saved_Games() {
    }

    public Saved_Games(String objectId, int ID, Date updated, Date created, String firstUserID, int firstUserResult,
                       int secondUserResult, String secondUserID, int stopTheGame, Boolean fbGame,
                       String firstUserName, String secondUserName, String firstUserDeviceID,
                       String secondUserDeviceID, String activity, String whosTurn) {
        this.objectId = objectId;
        this.ID = ID;
        this.updated = updated;
        this.created = created;
        this.firstUserID = firstUserID;
        this.firstUserResult = firstUserResult;
        this.secondUserResult = secondUserResult;
        this.secondUserID = secondUserID;
        this.stopTheGame = stopTheGame;
        this.fbGame = fbGame;
        this.firstUserName = firstUserName;
        this.secondUserName = secondUserName;
        this.firstUserDeviceID = firstUserDeviceID;
        this.secondUserDeviceID = secondUserDeviceID;
        Activity = activity;
        WhosTurn = whosTurn;
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

    public String getFirstUserID() {
        return firstUserID;
    }

    public void setFirstUserID(String firstUserID) {
        this.firstUserID = firstUserID;
    }

    public int getFirstUserResult() {
        return firstUserResult;
    }

    public void setFirstUserResult(int firstUserResult) {
        this.firstUserResult = firstUserResult;
    }

    public String getSecondUserID() {
        return secondUserID;
    }

    public void setSecondUserID(String secondUserID) {
        this.secondUserID = secondUserID;
    }

    public int getSecondUserResult() {
        return secondUserResult;
    }

    public void setSecondUserResult(int secondUserResult) {
        this.secondUserResult = secondUserResult;
    }

    public int getStopTheGame() {
        return stopTheGame;
    }

    public void setStopTheGame(int stopTheGame) {
        this.stopTheGame = stopTheGame;
    }

    public Boolean getFbGame() {
        return fbGame;
    }

    public void setFbGame(Boolean fbGame) {
        this.fbGame = fbGame;
    }

    public String getFirstUserName() {
        return firstUserName;
    }

    public void setFirstUserName(String firstUserName) {
        this.firstUserName = firstUserName;
    }

    public String getSecondUserName() {
        return secondUserName;
    }

    public void setSecondUserName(String secondUserName) {
        this.secondUserName = secondUserName;
    }

    public String getFirstUserDeviceID() {
        return firstUserDeviceID;
    }

    public void setFirstUserDeviceID(String firstUserDeviceID) {
        this.firstUserDeviceID = firstUserDeviceID;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getSecondUserDeviceID() {
        return secondUserDeviceID;
    }

    public void setSecondUserDeviceID(String secondUserDeviceID) {
        this.secondUserDeviceID = secondUserDeviceID;
    }

    public String getWhosTurn() {
        return WhosTurn;
    }

    public void setWhosTurn(String whosTurn) {
        WhosTurn = whosTurn;
    }
}
