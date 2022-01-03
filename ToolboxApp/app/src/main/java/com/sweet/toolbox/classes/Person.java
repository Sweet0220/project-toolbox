package com.sweet.toolbox.classes;

import java.io.Serializable;

public class Person implements Serializable {

    public int id;
    public String firstName, lastName;

    public Person(int id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
