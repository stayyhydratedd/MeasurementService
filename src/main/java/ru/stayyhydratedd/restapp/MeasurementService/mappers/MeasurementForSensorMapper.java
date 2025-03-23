package ru.stayyhydratedd.restapp.MeasurementService.mappers;

import org.mapstruct.Mapper;
import ru.stayyhydratedd.restapp.MeasurementService.dto.MeasurementForSensorDTO;
import ru.stayyhydratedd.restapp.MeasurementService.models.Measurement;

@Mapper(componentModel = "spring")
public interface MeasurementForSensorMapper {

    MeasurementForSensorDTO measurementToDTO(Measurement measurement);
    Measurement DTOToMeasurement(MeasurementForSensorDTO measurementDTO);
}
