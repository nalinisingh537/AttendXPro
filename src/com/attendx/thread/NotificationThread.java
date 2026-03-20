package com.attendx.thread;

import com.attendx.model.Student;
import java.util.*;

public class NotificationThread extends Thread {
    private Map<String, Student> students;
    private boolean running = true;
    
    public NotificationThread(Map<String, Student> students) {
        this.students = students;
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(30000);
                for (Student s : students.values()) {
                    if (s.getAttendancePercentage() < 60) {
                        System.out.println("\n⚠️ ALERT: " + s.getName() + " has " + 
                                           String.format("%.2f%%", s.getAttendancePercentage()) + " attendance!");
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public void stopThread() {
        running = false;
        interrupt();
    }
}