package ru.stayyhydratedd.restapp.MeasurementService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stayyhydratedd.restapp.MeasurementService.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findByRaining(boolean raining);
}
