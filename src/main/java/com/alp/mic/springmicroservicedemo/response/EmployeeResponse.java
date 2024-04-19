package com.alp.mic.springmicroservicedemo.response;

import jakarta.persistence.Column;

public class EmployeeResponse {

    private int id;

    private String name;

    private String email;

    private String bloodgroup; //we don't return this field in response, skipped/masked in bean mapper

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }


    @Override
    public String toString() {
        return "EmployeeResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
