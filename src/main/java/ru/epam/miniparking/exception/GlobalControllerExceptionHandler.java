package ru.epam.miniparking.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private final String DUPLICATE_KEY = "Field should be unique";
    private final String BAD_VALIDATION = "Check input for errors and try again";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> handleDataIntegrityViolationException(Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                HttpStatus.CONFLICT.value(),
                ex.getCause().getMessage(),
                DUPLICATE_KEY,
                ex.getMessage()
        );
        ResponseEntity<ErrorInfo> errorInfoResponseEntity = new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.CONFLICT);
        return errorInfoResponseEntity;
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorInfo> handleTransactionSystemException(Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                HttpStatus.BAD_REQUEST.value(),
                ex.getCause().getMessage(),
                BAD_VALIDATION,
                ex.getMessage()
        );

        ResponseEntity<ErrorInfo> errorInfoResponseEntity = new ResponseEntity<>(
                errorInfo,
                HttpStatus.BAD_REQUEST
        );

        return errorInfoResponseEntity;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundException(MiniparkingException ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                ex.getHttpStatus().value(),
                ex.getMessage()
        );

        ResponseEntity<ErrorInfo> errorInfoResponseEntity = new ResponseEntity<>(
                errorInfo,
                ex.getHttpStatus()
        );

        return errorInfoResponseEntity;
    }

    @ExceptionHandler(MiniparkingException.class)
    public ResponseEntity<ErrorInfo> handleMiniparkingException(MiniparkingException ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                ex.getHttpStatus().value(),
                ex.getMessage()
        );

        ResponseEntity<ErrorInfo> errorInfoResponseEntity = new ResponseEntity<>(
                errorInfo,
                ex.getHttpStatus()
        );

        return errorInfoResponseEntity;
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorInfo> handleConflictException(MiniparkingException ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                ex.getHttpStatus().value(),
                ex.getMessage()
        );

        ResponseEntity<ErrorInfo> errorInfoResponseEntity = new ResponseEntity<>(
                errorInfo,
                ex.getHttpStatus()
        );

        return errorInfoResponseEntity;
    }
}
