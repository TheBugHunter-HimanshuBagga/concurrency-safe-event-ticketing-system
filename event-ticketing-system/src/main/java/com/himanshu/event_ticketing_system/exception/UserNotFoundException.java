package com.himanshu.event_ticketing_system.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User Not Found");
    }
}
/*
Service throws UserNotFoundException
↓
Spring checks handlers
↓
Finds RuntimeException handler / Specific exception  Handler (NEW)
↓
Uses that handler
 */
