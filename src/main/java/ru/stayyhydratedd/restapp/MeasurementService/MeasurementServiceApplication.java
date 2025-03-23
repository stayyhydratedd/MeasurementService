package ru.stayyhydratedd.restapp.MeasurementService;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MeasurementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasurementServiceApplication.class, args);
	}

	@Bean
	public Faker faker(){
		return new Faker();
	}
}
