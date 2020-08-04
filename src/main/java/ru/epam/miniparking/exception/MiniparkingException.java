package ru.epam.miniparking.exception;

import org.springframework.http.HttpStatus;

public class MiniparkingException extends RuntimeException {
    private final static String CANNOT_PROCESS_MESSAGE = "Cannot proccess %s with id %d and name %s";
    private HttpStatus httpStatus;

    public MiniparkingException(String className, Long id, String name, HttpStatus httpStatus) {
        super(String.format(
                CANNOT_PROCESS_MESSAGE,
                className,
                id,
                name
        ));
        this.httpStatus = httpStatus;
    }

    public MiniparkingException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
