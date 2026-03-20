package com.attendx.model;

public class Student extends User {
    private static final long serialVersionUID = 1L;
    private int totalClasses;
    private int attendedClasses;
    
    public Student(String uid, String name, String department) {
        super(uid, name, department);
        this.totalClasses = 0;
        this.attendedClasses = 0;
    }
    
    public void markAttendance(boolean present) {
        totalClasses++;
        if (present) attendedClasses++;
    }
    
    public void addMedicalLeave() {
        totalClasses++;
    }
    
    public double getAttendancePercentage() {
        if (totalClasses == 0) return 100.0;
        return (attendedClasses * 100.0) / totalClasses;
    }
    
    public boolean isEligibleForExam() {
        return getAttendancePercentage() >= 75.0;
    }
    
    public int getClassesNeeded() {
        double percent = getAttendancePercentage();
        if (percent >= 75) return 0;
        double needed = (0.75 * totalClasses - attendedClasses) / 0.25;
        return (int) Math.ceil(needed);
    }
    
    public String getRiskLevel() {
        double p = getAttendancePercentage();
        if (p >= 75) return "SAFE";
        if (p >= 60) return "AT RISK";
        return "CRITICAL";
    }
    
    public int getTotalClasses() { return totalClasses; }
    public int getAttendedClasses() { return attendedClasses; }
    
    @Override
    public String getRole() { return "STUDENT"; }
    
    @Override
    public void displayInfo() {
        System.out.println("\n--- STUDENT DETAILS ---");
        System.out.println("Name: " + getName());
        System.out.println("Roll No: " + getUid());
        System.out.println("Department: " + getDepartment());
        System.out.println("Total Classes: " + totalClasses);
        System.out.println("Attended: " + attendedClasses);
        System.out.printf("Attendance: %.2f%%\n", getAttendancePercentage());
        System.out.println("Eligible: " + (isEligibleForExam() ? "YES" : "NO"));
        System.out.println("Risk: " + getRiskLevel());
        if (!isEligibleForExam()) {
            System.out.println("Classes needed: " + getClassesNeeded());
        }
    }
}