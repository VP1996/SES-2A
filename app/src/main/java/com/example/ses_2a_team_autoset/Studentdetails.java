package com.example.ses_2a_team_autoset;

public class Studentdetails {
        private String time;   // comes from the subject
        private String location;
        private String group;
        private String subject;
        private String tut;


        public Studentdetails() {
        }

        public Studentdetails( String time, String location,String group) {

            this.time = time;
            this.location = location;
            this.group = group;




        }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTut() {
        return tut;
    }

    public void setTut(String subject) {
        this.tut = tut;
    }
}




