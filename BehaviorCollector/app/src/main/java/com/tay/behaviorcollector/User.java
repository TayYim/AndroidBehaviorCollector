package com.tay.behaviorcollector;

public class User {
    private static String userID;

    private static int actionTimes = 3; // default:3

    public User(String userID) {
        this.userID = userID;
    }

    public static String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public static int getActionTimes() {
        return actionTimes;
    }

    public static void setActionTimes(int times) {
        actionTimes = times;
    }
}
