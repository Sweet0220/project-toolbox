package com.sweet.toolbox.classes;

import java.io.Serializable;

public class AppData implements Serializable {

    public int numberOfPeople=0, numberOfVehicles=0, numberOfJobs=0, lastPersonInteraction=0, lastVehicleInteraction=0, lastJobInteraction=0;
    public Person[] personArray = new Person[50];
    public Vehicle[] vehicleArray = new Vehicle[100];
    public Job[] jobArray = new Job[200];

    public AppData() {
        super();
    }
}
