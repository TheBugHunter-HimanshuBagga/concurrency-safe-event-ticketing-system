package com.himanshu.event_ticketing_system.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User Not Found");
    }
}
