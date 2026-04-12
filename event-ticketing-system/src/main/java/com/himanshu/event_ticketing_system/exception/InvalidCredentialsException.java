package com.himanshu.event_ticketing_system.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super("Invalid Email or Password");
    }
}
