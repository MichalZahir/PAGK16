package com.example.michalzahir.pagk16;

import com.backendless.Backendless;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by zahirm on 2016-06-01.
 */
public final class playersQueue {


    static Queue<String> PlayersQueue = new LinkedList<String>();
    private static playersQueue playersQueueInstance = null;

    public static playersQueue getPlayersQueueInstance(){

    if(playersQueueInstance == null){

        playersQueueInstance = new playersQueue();
    }
      return playersQueueInstance;

    }
    public Queue<String> get() {
        return PlayersQueue;
    }

    public static void AddUserToPlayersQueue() {
        String currentUserObjectId = Backendless.UserService.loggedInUser();

        synchronized (PlayersQueue) {
            PlayersQueue.add(currentUserObjectId);
        }
    }

    public static void AddUserToPlayersQueue(String CurrentUserObjectID) {
        synchronized (PlayersQueue) {
            PlayersQueue.add(CurrentUserObjectID);
        }
    }

    public static void RemoveUserFromPlayersQueue() {
        String currentUserObjectId = Backendless.UserService.loggedInUser();
        PlayersQueue.remove(currentUserObjectId);
    }

    public static void RemoveUserFromPlayersQueue(String CurrentUserObjectID) {
        PlayersQueue.remove(CurrentUserObjectID);
    }
    public boolean isEmpty() {
        return PlayersQueue.isEmpty();
    }

}
