package com.example.ses_2a_team_autoset;


public class CurrentUser {
    private static String FirstName;
    private static String LastName;
    private static String ID;
    private static String Type;


    //private String Subject1;
    public static String getFirstName() {
        return CurrentUser.FirstName;
    }
    public void setFirstName(String FirstName) {
        CurrentUser.FirstName = FirstName;
    }
    public static String getLastName() {
        return CurrentUser.LastName;
    }
    public void setLastName(String LastName) {
        CurrentUser.LastName = LastName;
    }
    public static String getID() {
        return CurrentUser.ID;
    }
    public void setID(String ID) {
        CurrentUser.ID = ID;
    }
    public static String getType() {
        return CurrentUser.Type;
    }
    public void setType(String Type) {
        CurrentUser.Type = Type;
    }

}
