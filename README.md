# AttendX Pro - Attendance Management System

## How to Run
1. Open terminal in VS Code (Ctrl + `)
2. Compile: `javac src/Main.java`
3. Run: `java -cp src Main`

## Features
- Faculty marks attendance
- Students check eligibility
- Background alerts for low attendance
## OOP Concepts Used
- **Abstraction:** Abstract User class
- **Inheritance:** Student and Faculty extend User
- **Encapsulation:** Private fields with getters/setters
- **Polymorphism:** Overridden methods getRole() and displayInfo()
- **Exception Handling:** Custom exceptions
- **Collections:** HashMap for O(1) student lookup
- **Threads:** Background NotificationThread for alerts