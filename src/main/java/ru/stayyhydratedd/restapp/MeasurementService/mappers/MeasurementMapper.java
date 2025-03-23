package ru.stayyhydratedd.restapp.MeasurementService.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.stayyhydratedd.restapp.MeasurementService.dto.MeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.models.Measurement;

@Mapper(uses = SensorForMeasurementMapper.class, componentModel = "spring")
public interface MeasurementMapper {

    @Mapping(source = "measuredBy", target = "sensor")
    MeasurementDTO measurementToDTO(Measurement measurement);
    @Mapping(source = "sensor", target = "measuredBy")
    Measurement DTOToMeasurement(MeasurementDTO measurementDTO);
}
