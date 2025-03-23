package ru.stayyhydratedd.restapp.MeasurementService.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {

    @GetMapping("/chart")
    public String generateChart() {
        return "chart";
    }
}
