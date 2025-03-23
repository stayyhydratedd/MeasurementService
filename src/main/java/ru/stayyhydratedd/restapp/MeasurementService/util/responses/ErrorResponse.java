package ru.stayyhydratedd.restapp.MeasurementService.util.responses;

import lombok.Data;

@Data
public class ErrorResponse {
    String message;
    long timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
