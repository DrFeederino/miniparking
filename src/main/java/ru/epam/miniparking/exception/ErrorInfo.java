package ru.epam.miniparking.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ErrorInfo {
    private int status;
    private String error;
    private String message;
    private String reason;

    public ErrorInfo() {
    }

    public ErrorInfo(int status, String error, String message, String reason) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.reason = reason;
    }

    public ErrorInfo(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
