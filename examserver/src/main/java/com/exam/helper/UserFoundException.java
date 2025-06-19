package com.exam.helper;

public class UserFoundException extends Exception {
    public UserFoundException() {
        super("User already exist with username: ");
    }
    public UserFoundException(String msg) {
        super(msg);
    }
}
