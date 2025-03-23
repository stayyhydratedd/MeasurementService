package ru.stayyhydratedd.restapp.MeasurementService.util.exceptions;

public class MeasurementNotFoundException extends RuntimeException {
    public MeasurementNotFoundException(String message) {
        super(message);
    }
}
