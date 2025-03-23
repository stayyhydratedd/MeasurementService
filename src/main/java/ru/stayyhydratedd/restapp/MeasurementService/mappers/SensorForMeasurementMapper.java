package ru.stayyhydratedd.restapp.MeasurementService.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorForMeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.models.Sensor;

@Mapper(componentModel = "spring")
public interface SensorForMeasurementMapper {

    @Mapping(source = "createdBy", target = "owner")
    SensorForMeasurementDTO sensorToDTO(Sensor sensor);
    @Mapping(source = "owner", target = "createdBy")
    Sensor DTOToSensor(SensorForMeasurementDTO sensorForMeasurementDTO);
}
