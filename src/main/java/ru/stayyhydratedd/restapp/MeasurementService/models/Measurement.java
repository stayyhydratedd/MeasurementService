package ru.stayyhydratedd.restapp.MeasurementService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measurement")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurement_seq")
    @SequenceGenerator(name = "measurement_seq", sequenceName = "measurement_id_seq", allocationSize = 1)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "value")
    @NotNull(message = "Value must not be null")
    @Min(value = -100, message = "Value can't be lower than -100 degrees")
    @Max(value = 100, message = "Value must not exceed 100 degrees")
    private double value;

    @Column(name = "raining")
    @NotNull(message = "Raining must not be null")
    private Boolean raining;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measured_by", referencedColumnName = "id")
    @NotNull(message = "Sensor for measurements is not specified")
    private Sensor measuredBy;

    @Column(name = "measured_at")
    @NotNull(message = "Measurement time should not be null")
    private LocalDateTime measuredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
