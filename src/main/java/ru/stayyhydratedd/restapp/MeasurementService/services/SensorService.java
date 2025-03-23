package ru.stayyhydratedd.restapp.MeasurementService.services;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorDTO;
import ru.stayyhydratedd.restapp.MeasurementService.dto.SensorForMeasurementDTO;
import ru.stayyhydratedd.restapp.MeasurementService.mappers.SensorForMeasurementMapper;
import ru.stayyhydratedd.restapp.MeasurementService.mappers.SensorMapper;
import ru.stayyhydratedd.restapp.MeasurementService.models.Sensor;
import ru.stayyhydratedd.restapp.MeasurementService.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final Faker faker;
    private final SensorMapper sensorMapper;
    private final SensorForMeasurementMapper sensorForMeasurementMapper;

    @Autowired
    public SensorService(SensorRepository sensorRepository, Faker faker, SensorMapper sensorMapper, SensorForMeasurementMapper sensorForMeasurementMapper) {
        this.sensorRepository = sensorRepository;
        this.faker = faker;
        this.sensorMapper = sensorMapper;
        this.sensorForMeasurementMapper = sensorForMeasurementMapper;
    }

    public List<Sensor> findAllSensors() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> findSensorById(int id) {
        return sensorRepository.findById(id);
    }

    public List<SensorDTO> findAllSensorsDTO(){

        return this.findAllSensors().stream()
               .map(sensorMapper::sensorToDTO)
               .toList();
    }

    public SensorDTO sensorToDTO(Sensor sensor) {
        return sensorMapper.sensorToDTO(sensor);
    }

    public Sensor DTOToSensor(SensorDTO sensorDTO) {
        return sensorMapper.DTOToSensor(sensorDTO);
    }

    public List<SensorDTO> sensorsToSensorsDTO(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorToDTO).toList();
    }

    public List<Sensor> sensorsDTOtoSensors(List<SensorDTO> sensorsDTO) {
        return sensorsDTO.stream().map(this::DTOToSensor).toList();
    }

    public Optional<Sensor> findSensorByNameAndCreatedBy(String name, String createdBy) {
        return sensorRepository.findByNameAndCreatedBy(name, createdBy);
    }

    @Transactional
    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }

    @Transactional
    public void enrichAndSaveSensorDTO(SensorForMeasurementDTO sensorForMeasurementDTO){
        enrichAndSaveSensor(sensorForMeasurementMapper.DTOToSensor(sensorForMeasurementDTO));
    }

    @Transactional
    public void saveAll(List<Sensor> sensors){
        sensorRepository.saveAll(sensors);
    }

    @Transactional
    public void createRandomSensors(int count){
        long start = System.currentTimeMillis();
        saveAll(IntStream.range(0, count)
                .mapToObj(_ -> Sensor.builder()
                        .name(faker.cat().name())   // да, для генерации названий сенсоров я использую случайные клички котов
                        .createdAt(LocalDateTime.now())
                        .createdBy(faker.company().name())
                        .build())
                .toList());
        System.out.println("batch-insert took: " + (System.currentTimeMillis() - start) + "ms");

    }

    @Transactional
    public void enrichAndSaveSensor(Sensor sensor){

        if(sensor.getCreatedBy().isEmpty()){
            sensor.setCreatedBy(faker.company().name());
        }
        sensor.setCreatedAt(LocalDateTime.now());
        save(sensor);
    }
}
