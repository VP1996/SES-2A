package com.example.ses_2a_team_autoset;

public class AddSubjectToClassesView {
    private String tutorialAct;
    private String daytime;
    private String location;
    private String group;

    public AddSubjectToClassesView(String text1) {
        tutorialAct = text1;
    }

    public String getTutorialAct() {
        return tutorialAct;
    }

}


// 6 values because 2 for top , 2time is day and time, 1 location and 1 for group