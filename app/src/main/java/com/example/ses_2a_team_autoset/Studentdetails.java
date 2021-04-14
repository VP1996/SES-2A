package com.example.ses_2a_team_autoset;

public class Studentdetails {
        private String subject;
        private String time;
        private String location;
        private String group;
        private String request;
        private boolean permission;

        public Studentdetails() {
        }

        public Studentdetails(String subject, String time, String location,String group, boolean permission) {
            this.subject = subject;
            this.time = time;
            this.location = location;
            this.group = group;

            this.permission = permission;
        }

        public String getsubject() {
            return subject;
        }

        public void setsubject(String subject) {
            this.subject = subject;
        }

        public String gettime() {
            return time;
        }

        public void settime(String time) {
            this.time = time;
        }

        public String getlocation() {
            return location;
        }

        public void setlocation(String location) {
            this.location = location;
        }


    public String getgroup() {
        return group;
    }

    public void setgroup(String group) {
        this.group = group;
    }



    public boolean getPermission() {
            return permission;
        }

        public void setPermission(boolean permission) {
            this.permission = permission;
        }
    }