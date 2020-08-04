package ru.epam.miniparking.exception;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorInfo {
    private int status;
    private String error;
    private String message;
    private String reason;

    public ErrorInfo(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
