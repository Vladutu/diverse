package com.ace.ucv;

import java.util.List;

public class Organization {

    private String myId;

    private String name;

    private Address address;

    private List<String> forms;

    public Organization(String myId, String name, Address address, List<String> forms) {
        this.myId = myId;
        this.name = name;
        this.address = address;
        this.forms = forms;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getForms() {
        return forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }
}
