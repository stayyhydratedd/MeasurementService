package ru.stayyhydratedd.restapp.MeasurementService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stayyhydratedd.restapp.MeasurementService.dto.MeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.services.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/api/charts")
public class RESTChartController {

    private final MeasurementService measurementService;

    @Autowired
    public RESTChartController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/data")
    public ResponseEntity<List<MeasurementDTO>> getChartData() {
        List<MeasurementDTO> measurements = measurementService.findAllMeasurementsDTO();
        return ResponseEntity.ok(measurements);
    }
}
