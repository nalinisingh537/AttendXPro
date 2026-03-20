package com.attendx.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uid;
    private String name;
    private String department;
    
    public User(String uid, String name, String department) {
        this.uid = uid;
        this.name = name;
        this.department = department;
    }
    
    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    
    public abstract String getRole();
    public abstract void displayInfo();
}