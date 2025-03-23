package ru.stayyhydratedd.restapp.MeasurementService.validators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorDTO;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorForMeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.models.Sensor;
import ru.stayyhydratedd.restapp.MeasurementService.services.SensorService;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorForMeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorForMeasurementDTO sensorDTO = (SensorForMeasurementDTO) target;

        Optional<Sensor> foundSensor =
                sensorService.findSensorByNameAndCreatedBy(
                        sensorDTO.getName(), sensorDTO.getOwner());

        if (foundSensor.isPresent()) {
            errors.rejectValue("name", null,
                    "The owner '"
                            + sensorDTO.getOwner()
                            + "' already has a sensor with that name");
        }
    }
}
