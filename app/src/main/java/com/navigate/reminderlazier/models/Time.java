package com.navigate.reminderlazier.models;

import java.util.Date;

/**
 * Created by BaLinh on 1/20/2018.
 */

public class Time {
    private Date time;

    private String creator;

    private String reminderName;

    private String location;

    public Time(Date time, String creator, String reminderName, String location) {
        this.time = time;
        this.creator = creator;
        this.reminderName = reminderName;
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
