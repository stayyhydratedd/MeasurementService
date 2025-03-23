package ru.stayyhydratedd.restapp.MeasurementService.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorDTO;
import ru.stayyhydratedd.restapp.MeasurementService.models.Sensor;

@Mapper(uses = MeasurementForSensorMapper.class, componentModel = "spring")
public interface SensorMapper {

    @Mapping(source = "createdBy", target = "owner")
    @Mapping(source = "measurements", target = "measurements")
    SensorDTO sensorToDTO(Sensor sensor);

    @Mapping(source = "owner", target = "createdBy")
    @Mapping(source = "measurements", target = "measurements")
    Sensor DTOToSensor(SensorDTO sensorDTO);
}
