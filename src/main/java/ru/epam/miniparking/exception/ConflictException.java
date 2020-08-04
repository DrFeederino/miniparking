package ru.epam.miniparking.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends MiniparkingException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
