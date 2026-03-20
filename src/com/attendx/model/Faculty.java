package com.attendx.model;

public class Faculty extends User {
    private static final long serialVersionUID = 1L;
    private String designation;
    
    public Faculty(String uid, String name, String department, String designation) {
        super(uid, name, department);
        this.designation = designation;
    }
    
    public String getDesignation() { return designation; }
    
    @Override
    public String getRole() { return "FACULTY"; }
    
    @Override
    public void displayInfo() {
        System.out.println("\n--- FACULTY DETAILS ---");
        System.out.println("Name: " + getName());
        System.out.println("UID: " + getUid());
        System.out.println("Department: " + getDepartment());
        System.out.println("Designation: " + designation);
    }
}