package com.sweet.toolbox.classes;

import java.io.Serializable;

public class Job implements Serializable {

    public int id, vehicleID, day, month, year, brakeIcon, engineIcon, suspensionIcon, otherIcon, status;
    public String jobDetails;
    public double cost;

    public Job(int id, int vehicleID, int day, int month, int year, int brakeIcon, int engineIcon, int suspensionIcon,int otherIcon, String jobDetails, double cost, int status)
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
        this.cost = cost;
        this.status = status;
    }

}
