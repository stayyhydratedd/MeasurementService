package ru.stayyhydratedd.restapp.MeasurementService.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeasurementForSensorDTO {

    @NotNull(message = "Value must not be empty")
    @Min(value = -100, message = "Value can't be lower than -100 degrees")
    @Max(value = 100, message = "Value must not exceed 100 degrees")
    private double value;

    @NotNull(message = "Raining must not be empty")
    private Boolean raining;
}
