package ru.stayyhydratedd.restapp.MeasurementService.util.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import ru.stayyhydratedd.restapp.MeasurementService.dto.MeasurementDTO;

import java.util.List;

@Getter
@JsonPropertyOrder({"count", "foundMeasurements"})
public class MeasurementDTOResponse {
    private final int count;
    private final List<MeasurementDTO> foundMeasurements;

    public MeasurementDTOResponse(List<MeasurementDTO> foundMeasurements) {
        this.count = foundMeasurements.size();
        this.foundMeasurements = foundMeasurements;
    }
}
