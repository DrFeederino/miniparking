package ru.epam.miniparking.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final String DUPLICATE_KEY = "Field should be unique";
    private static final String BAD_VALIDATION = "Check input for errors and try again";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> handleDataIntegrityViolationException(Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                HttpStatus.CONFLICT.value(),
                ex.getCause().getMessage(),
                DUPLICATE_KEY,
                ex.getMessage()
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorInfo> handleTransactionSystemException(Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                HttpStatus.BAD_REQUEST.value(),
                ex.getCause().getMessage(),
                BAD_VALIDATION,
                ex.getMessage()
        );

        return new ResponseEntity<>(
                errorInfo,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundException(NotFoundException ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                ex.getHttpStatus().value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(
                errorInfo,
                ex.getHttpStatus()
        );
    }

    @ExceptionHandler(MiniparkingException.class)
    public ResponseEntity<ErrorInfo> handleMiniparkingException(MiniparkingException ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                ex.getHttpStatus().value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(
                errorInfo,
                ex.getHttpStatus()
        );

    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorInfo> handleConflictException(ConflictException ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                ex.getHttpStatus().value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(
                errorInfo,
                ex.getHttpStatus()
        );
    }
}
