package com.attendx.exception;

public class DuplicateEntryException extends Exception {
    public DuplicateEntryException(String message) {
        super(message);
    }
}