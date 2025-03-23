package ru.stayyhydratedd.restapp.MeasurementService.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorDTO;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorForMeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.models.Sensor;
import ru.stayyhydratedd.restapp.MeasurementService.services.SensorService;
import ru.stayyhydratedd.restapp.MeasurementService.util.responses.ErrorResponse;
import ru.stayyhydratedd.restapp.MeasurementService.util.exceptions.SensorNotCreatedException;
import ru.stayyhydratedd.restapp.MeasurementService.util.responses.SensorDTOResponse;
import ru.stayyhydratedd.restapp.MeasurementService.validators.SensorValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensors")
public class RESTSensorController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public RESTSensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/new")
    public ResponseEntity<String> createSensor(@RequestBody @Valid SensorForMeasurementDTO sensorDTO,
                                                   BindingResult bindingResult){
        sensorValidator.validate(sensorDTO, bindingResult);

        String message = "'" + sensorDTO.getOwner() + "' successfully created a sensor named '" + sensorDTO.getName() + "'";

        if(bindingResult.hasErrors()){

            message = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining(" ; "));

            throw new SensorNotCreatedException(message);
        } else if(sensorDTO.getOwner().isEmpty()){
            message = "Sensor owner is not specified, this field will be generated";
        }

        sensorService.enrichAndSaveSensorDTO(sensorDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/new/{count}")
    public ResponseEntity<HttpStatus> createRandomSensors(@PathVariable("count") int count){
        if(count < 1 || count > 1000){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        sensorService.createRandomSensors(count);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public SensorDTOResponse getSensorsAsDTO(){
        return new SensorDTOResponse(sensorService.findAllSensorsDTO());
    }

    @GetMapping("/findAllBy")
    public SensorDTOResponse findSensorsBy(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String owner){

        List<SensorDTO> foundSensors = sensorService.findAllSensorsDTO();

        if(name != null)
            foundSensors = foundSensors.stream()
                    .filter(s -> s.getName().equals(name))
                    .toList();
        if(owner != null)
            foundSensors = foundSensors.stream()
                    .filter(s -> s.getOwner().equals(owner))
                    .toList();

        System.out.println(foundSensors);
        return new SensorDTOResponse(foundSensors);
    }

    @GetMapping("/id/{id}")
    public SensorDTOResponse getSensorById(@PathVariable("id") int id){
        Optional<Sensor> foundSensor = sensorService.findSensorById(id);
        if(foundSensor.isEmpty()){
            throw new SensorNotCreatedException("Sensor with id '" + id + "' not created");
        }
        return new SensorDTOResponse(
                Collections.singletonList(sensorService.sensorToDTO(foundSensor.get())));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(SensorNotCreatedException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
