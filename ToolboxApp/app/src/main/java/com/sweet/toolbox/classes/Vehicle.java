package com.sweet.toolbox.classes;

import java.io.Serializable;

public class Vehicle implements Serializable {

    int id, personID, typeOfVehicle;
    String brand, licensePlate;

    public Vehicle(int id, int personID, int typeOfVehicle, String brand, String licensePlate)
    {
        this.id = id;
        this.personID = personID;
        this.typeOfVehicle = typeOfVehicle;
        this.brand = brand;
        this.licensePlate = licensePlate;
    }

}
