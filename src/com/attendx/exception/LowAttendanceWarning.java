package com.attendx.exception;

public class LowAttendanceWarning extends Exception {
    public LowAttendanceWarning(String message) {
        super(message);
    }
}