package ru.stayyhydratedd.restapp.MeasurementService.util.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import ru.stayyhydratedd.restapp.MeasurementService.dto.MeasurementForSensorDTO;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorDTO;
import java.util.List;

@Getter
@JsonPropertyOrder({"count", "foundSensors"})
public class SensorDTOResponse {
    private final int count;
    private final List<SensorDTOForResponse> foundSensors;

    public SensorDTOResponse(List<SensorDTO> sensorsDTO) {
        count = sensorsDTO.size();
        foundSensors = sensorsDTO.stream().map(SensorDTOForResponse::new).toList();
    }

    @Getter
    @JsonPropertyOrder({"name", "owner", "measurements"})
    static class SensorDTOForResponse {
        private final String name;
        private final String owner;
        private final MeasurementsForSensorDTOResponse foundMeasurements;

        public SensorDTOForResponse(SensorDTO sensorDTO) {
            this.name = sensorDTO.getName();
            this.owner = sensorDTO.getOwner();
            foundMeasurements = new MeasurementsForSensorDTOResponse(sensorDTO.getMeasurements());
        }

        @Getter
        @JsonPropertyOrder({"count", "measurements"})
        static class MeasurementsForSensorDTOResponse {
            private final int count;
            private final List<MeasurementForSensorDTO> measurements;

            public MeasurementsForSensorDTOResponse(List<MeasurementForSensorDTO> measurements){
                this.measurements = measurements;
                this.count = measurements.size();
            }
        }
    }
}
