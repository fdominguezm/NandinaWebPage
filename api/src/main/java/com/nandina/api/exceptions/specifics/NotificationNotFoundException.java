package com.nandina.api.exceptions.specifics;

import com.nandina.api.exceptions.NotFoundException;

public class NotificationNotFoundException extends NotFoundException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}

