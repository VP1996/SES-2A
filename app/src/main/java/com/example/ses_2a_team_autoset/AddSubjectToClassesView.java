package com.example.ses_2a_team_autoset;

public class AddSubjectToClassesView {
    private String tutorialAct;
    private String daytime;
    private String location;
    private String group;

    public AddSubjectToClassesView(String text1,String text2,String text3,String text4) {
        tutorialAct = text1;
        daytime = text2;
        location = text3;
        group = text4;
    }

    public String getTutorialAct() {
        return tutorialAct;
    }
    public String getDaytime() {
        return daytime;
    }
    public String getLocation() {
        return location;
    }
    public String getGroup() {
        return group;
    }

}




// 6 values because 2 for top , 2time is day and time, 1 location and 1 for group