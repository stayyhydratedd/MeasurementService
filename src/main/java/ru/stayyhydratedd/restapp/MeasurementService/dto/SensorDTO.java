package ru.stayyhydratedd.restapp.MeasurementService.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class SensorDTO {

    @NotEmpty(message = "Sensor name should not be empty")
    private String name;

    private String owner;

    private List<MeasurementForSensorDTO> measurements;
}
