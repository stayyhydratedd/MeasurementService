package ru.stayyhydratedd.restapp.MeasurementService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stayyhydratedd.restapp.MeasurementService.models.Sensor;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByNameAndCreatedBy(String name, String createdBy);

    List<Sensor> findAllByName(String name);

    List<Sensor> findAllByCreatedBy(String createdBy);
}
