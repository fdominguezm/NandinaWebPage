package com.nandina.api.exceptions.specifics;


import com.nandina.api.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(){
        super("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

