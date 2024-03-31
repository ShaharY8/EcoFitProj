package com.example.ecofit.Model;

public class User {
    private String name;
    private String phone;
    private boolean isApproved;

    public User(String name, String phone, boolean isApproved) {
        this.name = name;
        this.phone = phone;
        this.isApproved = isApproved;
    }


    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.isApproved = false;
    }

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
