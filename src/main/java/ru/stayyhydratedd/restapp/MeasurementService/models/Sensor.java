package ru.stayyhydratedd.restapp.MeasurementService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    @SequenceGenerator(name = "sensor_seq", sequenceName = "sensor_id_seq", allocationSize = 1)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Sensor name should not be empty!")
    private String name;

    @Column(name = "created_at")
    @NotNull(message = "Sensor creation time should not be null!")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "measuredBy", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Measurement> measurements;

    public Sensor(String name, String createdBt){
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBt;
    }
}

