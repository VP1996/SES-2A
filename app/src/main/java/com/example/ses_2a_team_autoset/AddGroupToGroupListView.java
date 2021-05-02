package com.example.ses_2a_team_autoset;

public class AddGroupToGroupListView {
    private String Group;
    private String Students;
    public AddGroupToGroupListView(String text1,String text2) {
        Group = text1;
        Students = text2;
    }

    public String getGroup() {
        return Group;
    }
    public String getStudents() {
        return Students;
    }
}
