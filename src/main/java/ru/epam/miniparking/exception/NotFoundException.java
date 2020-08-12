package ru.epam.miniparking.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends MiniparkingException {
    private static final String CANNOT_FIND_ERROR = "Cannot find %s with id %d";

    public NotFoundException(String className, Long id) {
        super(String.format(CANNOT_FIND_ERROR, className, id), HttpStatus.NOT_FOUND);
    }
}
