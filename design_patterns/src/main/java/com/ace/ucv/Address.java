package com.ace.ucv;

public class Address {

    private String streetName;

    private ZipCode zipCode;

    public Address(String streetName, ZipCode zipCode) {
        this.streetName = streetName;
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }

    public void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }
}
