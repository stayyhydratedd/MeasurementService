package ru.stayyhydratedd.restapp.MeasurementService.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SensorForMeasurementDTO {

    @NotEmpty(message = "Sensor name should not be empty")
    private String name;

    private String owner;
}
