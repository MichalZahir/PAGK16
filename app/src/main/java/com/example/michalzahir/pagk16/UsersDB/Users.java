package com.example.michalzahir.pagk16.UsersDB;

import java.util.Date;

/**
 * Created by zahirm on 2016-07-07.
 */
public class Users {
    private String objectId;
    private int ID;
    private Date created;
    private Date updated;
    private String Device_ID;
    private String name;

    public Users() {
    }

    public Users(String objectId, int ID, Date created, Date updated, String device_ID, String name) {
        this.objectId = objectId;
        this.ID = ID;
        this.created = created;
        this.updated = updated;
        Device_ID = device_ID;
        this.name = name;
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
}
