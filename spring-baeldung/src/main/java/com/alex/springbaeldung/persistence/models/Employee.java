package com.alex.springbaeldung.persistence.models;

import javax.persistence.Entity;

//Entity to display and bind to the form
public class Employee {
    private String name;
    private long id;
    private String contactNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
