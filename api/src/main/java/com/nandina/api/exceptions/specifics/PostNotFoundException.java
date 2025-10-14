package com.nandina.api.exceptions.specifics;


import com.nandina.api.exceptions.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
