package ru.stayyhydratedd.restapp.MeasurementService.util;

import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleanup {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseCleanup(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PreDestroy
    private void clearDatabases() {
        jdbcTemplate.execute("truncate table measurement, sensor restart identity cascade;" +
                "ALTER SEQUENCE sensor_id_seq RESTART WITH 1;" +
                "ALTER SEQUENCE measurement_id_seq RESTART WITH 1");
        System.out.println("Databases successfully cleared before shutdown.");
    }

}
