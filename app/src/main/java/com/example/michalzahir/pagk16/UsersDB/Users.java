package com.example.michalzahir.pagk16.UsersDB;

import android.os.Parcel;
import android.os.Parcelable;

import com.backendless.BackendlessUser;

import java.util.Date;

/**
 * Created by zahirm on 2016-07-07.
 */
public class Users extends BackendlessUser implements Parcelable {
    private String objectId;
    private int ID;
    private Date created;
    private Date updated;
    private String Device_ID;
    private String name;
    private int DRAW;
    private int WON;
    private int LOST;
    private int RANKING;
    private int usersCount;
    private int POINTS;
    private String RANKINGARROW;
    private int OLDRANKING;
    private String AnsweredQuestionsIDs;
    private String FbProfile_ID;
    public Users() {
    }

    public Users(String objectId, int ID, Date created, Date updated, String device_ID, String name, int DRAW, int WON, int LOST, int RANKING,
                 int usersCount, int POINTS, String RANKINGARROW, int OLDRANKING, String AnsweredQuestionsIDs, String FbProfile_ID) {
        this.objectId = objectId;
        this.ID = ID;
        this.created = created;
        this.updated = updated;
        Device_ID = device_ID;
        this.name = name;
        this.DRAW = DRAW;
        this.WON = WON;
        this.LOST = LOST;
        this.RANKING=RANKING;
        this.usersCount=usersCount;
        this.POINTS = POINTS;
        this.RANKINGARROW = RANKINGARROW;
        this.OLDRANKING = OLDRANKING;
        this.AnsweredQuestionsIDs = AnsweredQuestionsIDs;
        this.FbProfile_ID = FbProfile_ID;
    }

    public String getAnsweredQuestionsIDs() {
        return AnsweredQuestionsIDs;
    }

    public void setAnsweredQuestionsIDs(String answeredQuestionsIDs) {
        AnsweredQuestionsIDs = answeredQuestionsIDs;
    }

    public int getOLDRANKING() {
        return OLDRANKING;
    }

    public void setOLDRANKING(int OLDRANKING) {
        this.OLDRANKING = OLDRANKING;
    }

    public String getRANKINGARROW() {
        return RANKINGARROW;
    }

    public void setRANKINGARROW(String RANKINGARROW) {
        this.RANKINGARROW = RANKINGARROW;
    }

    public int getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(int POINTS) {
        this.POINTS = POINTS;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getRANKING() {
        return RANKING;
    }

    public void setRANKING(int RANKING) {
        this.RANKING = RANKING;
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

    public String getDevice_ID() {
        return Device_ID;
    }

    public void setDevice_ID(String device_ID) {
        Device_ID = device_ID;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDRAW() {
        return DRAW;
    }

    public void setDRAW(int DRAW) {
        this.DRAW = DRAW;
    }

    public int getWON() {
        return WON;
    }

    public void setWON(int WON) {
        this.WON = WON;
    }

    public int getLOST() {
        return LOST;
    }

    public void setLOST(int LOST) {
        this.LOST = LOST;
    }

    public String getFbProfile_ID() {
        return FbProfile_ID;
    }

    public void setFbProfile_ID(String fbProfile_ID) {
        FbProfile_ID = fbProfile_ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { this.objectId,
                this.Device_ID,
                this.name, this.AnsweredQuestionsIDs,this.FbProfile_ID
        });
    }
    public Users(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        this.objectId =data[0];
        this.Device_ID = data[1];
        this.name =data[2];
        this.AnsweredQuestionsIDs =data[3];
        this.FbProfile_ID = data[4];
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
}
