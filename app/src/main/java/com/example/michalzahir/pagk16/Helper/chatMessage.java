package com.example.michalzahir.pagk16.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zahirm on 2016-11-01.
 */

public class chatMessage {
    public   String PublisherID = " ";
    public   List<String> messages = new ArrayList<>();
    public   int notificationID;

    public chatMessage() {
    }

    public chatMessage(String publisherID, List<String> messages, int notificationID) {
        PublisherID = publisherID;
        this.messages = messages;
        this.notificationID = notificationID;
    }

    public String getPublisherID() {
        return PublisherID;
    }

    public void setPublisherID(String publisherID) {
        PublisherID = publisherID;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
    public static chatMessage findChatMessageByPublisherID(String PublisherID, List<chatMessage> ChatMessages){
        chatMessage cm = null;
        if (ChatMessages==null)
            return null;
    for (chatMessage CMM: ChatMessages){
        System.out.println("CMM"+CMM.getPublisherID() + " CMM.getMessages"+CMM.getMessages()+" CMM.getNotificationID" + CMM.getNotificationID());
       if (CMM.getPublisherID().equals(PublisherID) ){

           cm = CMM;

       }

    }
        return cm;
    }
}
