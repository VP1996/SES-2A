package com.example.ses_2a_team_autoset;

public class AddMailToMailboxView {
    private String FullNameStudentID;
    private String SubjectClassType;
    private String CurrentGroup;
    private String ExpectedGroup;
    private String Reason;


    public AddMailToMailboxView(String text1, String text2, String text3, String text4, String text5) {
        FullNameStudentID = text1;
        SubjectClassType = text2;
        CurrentGroup = text3;
        ExpectedGroup = text4;
        Reason = text5;
    }

    public String getFullNameStudentID() {
        return FullNameStudentID;
    }
    public String getSubjectClassType() {
        return SubjectClassType;
    }
    public String getCurrentGroup() {
        return CurrentGroup;
    }
    public String getExpectedGroup() {
        return ExpectedGroup;
    }
    public String getReason() {
        return Reason;
    }
}
