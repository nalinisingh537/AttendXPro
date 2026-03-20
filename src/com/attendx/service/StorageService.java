package com.attendx.service;

import com.attendx.model.Student;
import java.io.*;
import java.util.*;

public class StorageService {
    private static final String DATA_DIR = "data/";
    private static final String STUDENTS_FILE = DATA_DIR + "students.ser";
    
    static {
        new File(DATA_DIR).mkdirs();
    }
    
    @SuppressWarnings("unchecked")
    public static void saveStudents(Map<String, Student> students) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENTS_FILE))) {
            oos.writeObject(students);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Student> loadStudents() throws IOException, ClassNotFoundException {
        File file = new File(STUDENTS_FILE);
        if (!file.exists()) return new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, Student>) ois.readObject();
        }
    }
}