package com.himanshu.event_ticketing_system.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid Email or Password");
    }
}
