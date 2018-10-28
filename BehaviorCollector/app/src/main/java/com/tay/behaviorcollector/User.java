package com.tay.behaviorcollector;

public class User {
    private static String userID;

    public User(String userID) {
        this.userID = userID;
    }

    public static String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
