package com.sweet.toolbox.classes;

import java.io.Serializable;

public class Job implements Serializable {

    int id, vehicleID, day, month, year, brakeIcon, engineIcon, suspensionIcon, otherIcon;
    String jobDetails;

    public Job(int id, int vehicleID, int day, int month, int year, int brakeIcon, int engineIcon, int suspensionIcon,int otherIcon, String jobDetails)
    {
        this.id = id;
        this.vehicleID = vehicleID;
        this.day = day;
        this.month = month;
        this.year = year;
        this.brakeIcon = brakeIcon;
        this.engineIcon = engineIcon;
        this.suspensionIcon = suspensionIcon;
        this.otherIcon = otherIcon;
        this.jobDetails = jobDetails;
    }

}
