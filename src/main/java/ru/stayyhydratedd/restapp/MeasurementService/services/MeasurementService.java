package ru.stayyhydratedd.restapp.MeasurementService.services;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stayyhydratedd.restapp.MeasurementService.dto.MeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.mappers.MeasurementMapper;
import ru.stayyhydratedd.restapp.MeasurementService.models.Measurement;
import ru.stayyhydratedd.restapp.MeasurementService.models.Sensor;
import ru.stayyhydratedd.restapp.MeasurementService.repositories.MeasurementRepository;
import ru.stayyhydratedd.restapp.MeasurementService.repositories.SensorRepository;
import ru.stayyhydratedd.restapp.MeasurementService.util.exceptions.SensorNotCreatedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    private final Faker faker;
    private final MeasurementMapper measurementMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository,
                              Faker faker, MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.faker = faker;
        this.measurementMapper = measurementMapper;
    }
    public Optional<Measurement> getMeasurement(Integer id) {
        return measurementRepository.findById(id);
    }
    public List<Measurement> findAllMeasurements() {
        return measurementRepository.findAll();
    }
    public List<MeasurementDTO> findAllMeasurementsDTO() {
        return findAllMeasurements().stream().map(this::measurementToDTO).toList();
    }

    public List<MeasurementDTO> measurementsToMeasurementsDTO(List<Measurement> measurements) {
        return measurements.stream()
                .map(measurementMapper::measurementToDTO)
                .toList();
    }
    public List<Measurement> measurementsDTOToMeasurements(List<MeasurementDTO> measurementsDTO) {
        return measurementsDTO.stream()
                .map(measurementMapper::DTOToMeasurement)
                .toList();
    }
    public MeasurementDTO measurementToDTO(Measurement measurement) {
        return measurementMapper.measurementToDTO(measurement);
    }
    public Measurement DTOToMeasurement(MeasurementDTO measurementDTO) {
        return measurementMapper.DTOToMeasurement(measurementDTO);
    }
    public List<MeasurementDTO> getMeasurementsDTOByRaining(boolean raining) {
        return measurementRepository.findByRaining(raining).stream()
                .map(measurementMapper::measurementToDTO)
                .toList();
    }
    @Transactional
    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    @Transactional
    public void saveAll(List<Measurement> measurements) {
        measurementRepository.saveAll(measurements);
    }

    @Transactional
    public void enrichAndSaveMeasurement(Measurement measurement) {
        Optional<Sensor> foundSensor = sensorRepository.findByNameAndCreatedBy(
                        measurement.getMeasuredBy().getName(),
                        measurement.getMeasuredBy().getCreatedBy());
        if (foundSensor.isPresent()) {
            measurement.setMeasuredBy(foundSensor.get());
        } else {
            throw new SensorNotCreatedException(
                    "Sensor with name '" + measurement.getMeasuredBy().getName()
                    + "' by owner '" + measurement.getMeasuredBy().getCreatedBy()
                    + "' was not found");
        }
        measurement.setMeasuredAt(LocalDateTime.now());
        this.save(measurement);
    }

    @Transactional
    public String saveRandomMeasurements(int count) {

        List<Sensor> sensors = sensorRepository.findAll();
        if(sensors.isEmpty()) {
            return "No sensors found!";
        }

        int sensorCount = sensors.size();
        saveAll(IntStream.range(0, count).mapToObj(_ -> Measurement.builder()
                        .value(faker.random().nextDouble(-40, 55))
                        .raining(faker.random().nextBoolean())
                        .measuredBy(sensors.get(faker.random().nextInt(sensorCount)))
                        .measuredAt(LocalDateTime.now())
                        .build())
                .toList()
        );
        return "Measurements (" + count + ") successfully saved!";
    }
}
