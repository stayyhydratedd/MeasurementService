package ru.stayyhydratedd.restapp.MeasurementService.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.stayyhydratedd.restapp.MeasurementService.dto.MeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.models.Measurement;
import ru.stayyhydratedd.restapp.MeasurementService.services.MeasurementService;
import ru.stayyhydratedd.restapp.MeasurementService.util.exceptions.MeasurementNotAddedException;
import ru.stayyhydratedd.restapp.MeasurementService.util.exceptions.MeasurementNotFoundException;
import ru.stayyhydratedd.restapp.MeasurementService.util.exceptions.SensorNotCreatedException;
import ru.stayyhydratedd.restapp.MeasurementService.util.responses.ErrorResponse;
import ru.stayyhydratedd.restapp.MeasurementService.util.responses.MeasurementDTOResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/measurements")
public class RESTMeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public RESTMeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping()
    public MeasurementDTOResponse getMeasurementsAsDTO(){

        List<MeasurementDTO> measurementsDTO = measurementService.findAllMeasurementsDTO();
        if(measurementsDTO.isEmpty()){
            throw new MeasurementNotFoundException("No measurements found");
        }
        return new MeasurementDTOResponse(measurementsDTO);
    }

    @GetMapping("/id/{id}")
    public MeasurementDTOResponse getMeasurementById(@PathVariable("id") Integer id) {
        Optional<Measurement> foundMeasurement = measurementService.getMeasurement(id);
        if(foundMeasurement.isEmpty()){
            throw new MeasurementNotFoundException("Measurement with id '" + id + "' not found");
        }
        return new MeasurementDTOResponse(
                Collections.singletonList(measurementService.measurementToDTO(foundMeasurement.get())));
    }

    @GetMapping("/findAllBy")
    public MeasurementDTOResponse findMeasurementsBy(@RequestParam(required = false) String raining,
                                                     @RequestParam(required = false) String sensorName,
                                                     @RequestParam(required = false) String sensorOwner,
                                                     @RequestParam(required = false) String valueFrom,
                                                     @RequestParam(required = false) String valueTill,
                                                     @RequestParam(required = false) String valueEquals) {

        List<MeasurementDTO> foundMeasurements = measurementService.findAllMeasurementsDTO();

        if(raining != null)
            foundMeasurements = foundMeasurements.stream()
                    .filter(m -> m.getRaining() == Boolean.parseBoolean(raining))
                    .toList();

        if(sensorName != null)
            foundMeasurements = foundMeasurements.stream()
                    .filter(m -> m.getSensor().getName().equals(sensorName))
                    .toList();

        if(sensorOwner != null)
            foundMeasurements = foundMeasurements.stream()
                    .filter(m -> m.getSensor().getOwner().equals(sensorOwner))
                    .toList();

        if(valueFrom != null)
            foundMeasurements = foundMeasurements.stream()
                    .filter(m -> m.getValue() >= Double.parseDouble(valueFrom))
                    .toList();

        if(valueTill != null)
            foundMeasurements = foundMeasurements.stream()
                    .filter(m -> m.getValue() <= Double.parseDouble(valueTill))
                    .toList();

        if(valueEquals != null && (valueFrom == null && valueTill == null))
            foundMeasurements = foundMeasurements.stream()
                    .filter(m -> Math.floor(m.getValue()) == Math.floor(Double.parseDouble(valueEquals)))
                    .toList();

        if (foundMeasurements.isEmpty())
            throw new MeasurementNotFoundException("No measurements found");

        return new MeasurementDTOResponse(foundMeasurements);
    }

    @GetMapping("/add/{count}")
    public String addRandomMeasurements(@PathVariable int count){
        if(count < 1 || count > 10000){
            return "Invalid 'count' value!";    // заменить
        }
        return measurementService.saveRandomMeasurements(count);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){

        String message = "Measurement added successfully";
        if (bindingResult.hasErrors()) {

            message = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(" ; "));
            throw new MeasurementNotAddedException(message);
        }

        Measurement measurement = measurementService.DTOToMeasurement(measurementDTO);
        measurementService.enrichAndSaveMeasurement(measurement);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @ExceptionHandler({MeasurementNotFoundException.class, MeasurementNotAddedException.class,
            SensorNotCreatedException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
