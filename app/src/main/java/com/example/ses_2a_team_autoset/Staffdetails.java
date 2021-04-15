package com.example.ses_2a_team_autoset;


public class Staffdetails {
    private String time;   // comes from the subject
    private String name;
    private String id;
    private String group;


    public Staffdetails() {
    }

    public Staffdetails( String time, String name,String id, String group) {

        this.time = time;
        this.name = name;
        this.group = group;
        this.id =id;




    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}




