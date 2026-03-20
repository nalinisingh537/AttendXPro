package com.attendx.controller;

import com.attendx.model.*;
import com.attendx.service.*;
import com.attendx.thread.NotificationThread;
import java.util.*;

public class AttendXController {
    private Map<String, Student> students;
    private Map<String, Faculty> faculty;
    private Scanner scanner;
    private NotificationThread notifier;
    
    public AttendXController() {
        students = new HashMap<>();
        faculty = new HashMap<>();
        scanner = new Scanner(System.in);
        loadData();
    }
    
    private void loadData() {
        try {
            students = StorageService.loadStudents();
            System.out.println("Loaded " + students.size() + " students.");
        } catch (Exception e) {
            students = new HashMap<>();
        }
    }
    
    private void saveData() {
        try {
            StorageService.saveStudents(students);
        } catch (Exception e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }
    
    public void start() {
        notifier = new NotificationThread(students);
        notifier.start();
        
        System.out.println("\n========================================");
        System.out.println("        ATTENDX PRO");
        System.out.println("    Attendance Management System");
        System.out.println("========================================");
        
        while (true) {
            System.out.println("\n1. Faculty Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            
            if (choice.equals("1")) facultyLogin();
            else if (choice.equals("2")) studentLogin();
            else if (choice.equals("3")) {
                notifier.stopThread();
                saveData();
                System.out.println("Goodbye!");
                break;
            }
            else System.out.println("Invalid!");
        }
    }
    
    private void facultyLogin() {
        System.out.print("Faculty UID: ");
        String uid = scanner.nextLine();
        
        Faculty f = faculty.get(uid);
        if (f == null) {
            System.out.println("Not found. Register? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) registerFaculty();
            return;
        }
        System.out.println("Welcome " + f.getName() + "!");
        facultyMenu();
    }
    
    private void registerFaculty() {
        System.out.print("UID: "); String uid = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Dept: "); String dept = scanner.nextLine();
        System.out.print("Designation: "); String desig = scanner.nextLine();
        faculty.put(uid, new Faculty(uid, name, dept, desig));
        System.out.println("Registered!");
    }
    
    private void facultyMenu() {
        while (true) {
            System.out.println("\n--- FACULTY MENU ---");
            System.out.println("1. Mark Attendance");
            System.out.println("2. Add Medical Leave");
            System.out.println("3. View Students");
            System.out.println("4. Student Details");
            System.out.println("5. Register Student");
            System.out.println("6. Logout");
            System.out.print("Choice: ");
            String ch = scanner.nextLine();
            
            if (ch.equals("1")) markAttendance();
            else if (ch.equals("2")) addMedicalLeave();
            else if (ch.equals("3")) viewAllStudents();
            else if (ch.equals("4")) viewStudentDetails();
            else if (ch.equals("5")) registerStudent();
            else if (ch.equals("6")) break;
            else System.out.println("Invalid!");
        }
    }
    
    private void markAttendance() {
        System.out.print("Student Roll No: ");
        String uid = scanner.nextLine();
        Student s = students.get(uid);
        if (s == null) { System.out.println("Not found!"); return; }
        
        System.out.print("Present? (y/n): ");
        boolean present = scanner.nextLine().equalsIgnoreCase("y");
        s.markAttendance(present);
        saveData();
        System.out.printf("Attendance: %.2f%%\n", s.getAttendancePercentage());
        if (!s.isEligibleForExam()) System.out.println("⚠️ WARNING: May become ineligible!");
    }
    
    private void addMedicalLeave() {
        System.out.print("Student Roll No: ");
        String uid = scanner.nextLine();
        Student s = students.get(uid);
        if (s == null) { System.out.println("Not found!"); return; }
        s.addMedicalLeave();
        saveData();
        System.out.printf("New attendance: %.2f%%\n", s.getAttendancePercentage());
    }
    
    private void viewAllStudents() {
        if (students.isEmpty()) { System.out.println("No students."); return; }
        System.out.println("\nRoll No | Name | Attendance | Risk");
        for (Student s : students.values()) {
            System.out.printf("%s | %s | %.2f%% | %s\n", 
                s.getUid(), s.getName(), s.getAttendancePercentage(), s.getRiskLevel());
        }
    }
    
    private void viewStudentDetails() {
        System.out.print("Roll No: ");
        Student s = students.get(scanner.nextLine());
        if (s != null) s.displayInfo();
        else System.out.println("Not found!");
    }
    
    private void registerStudent() {
        System.out.print("Roll No: "); String uid = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Dept: "); String dept = scanner.nextLine();
        students.put(uid, new Student(uid, name, dept));
        saveData();
        System.out.println("Student registered!");
    }
    
    private void studentLogin() {
        System.out.print("Roll No: ");
        Student s = students.get(scanner.nextLine());
        if (s == null) { System.out.println("Not found!"); return; }
        
        System.out.println("Welcome " + s.getName() + "!");
        while (true) {
            System.out.println("\n1. View Attendance");
            System.out.println("2. Check Exam Eligibility");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            String ch = scanner.nextLine();
            
            if (ch.equals("1")) {
                System.out.printf("\nAttendance: %.2f%%\n", s.getAttendancePercentage());
                System.out.println("Risk: " + s.getRiskLevel());
                if (!s.isEligibleForExam()) 
                    System.out.println("Need " + s.getClassesNeeded() + " more classes!");
            }
            else if (ch.equals("2")) {
                if (s.isEligibleForExam()) System.out.println("✓ ELIGIBLE for exam!");
                else System.out.println("✗ NOT ELIGIBLE! Need " + s.getClassesNeeded() + " classes.");
            }
            else if (ch.equals("3")) break;
        }
    }
}