package com.example.controller;

import com.example.model.Postcode;
import com.example.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/distance")
public class DistanceController {

    @Autowired
    private DistanceService service;

    @GetMapping("/{postcode1}/{postcode2}")
    public Map<String, Object> getDistance(@PathVariable String postcode1, @PathVariable String postcode2) {
        // Fetch postcode details (latitude, longitude)
        Postcode location1 = service.getPostcodeDetails(postcode1);
        Postcode location2 = service.getPostcodeDetails(postcode2);

        // Compute distance
        double distance = service.calculateDistance(postcode1, postcode2);

        // Return detailed JSON response
        return Map.of(
                "location1", Map.of(
                        "postcode", location1.getPostcode(),
                        "latitude", location1.getLatitude(),
                        "longitude", location1.getLongitude()
                ),
                "location2", Map.of(
                        "postcode", location2.getPostcode(),
                        "latitude", location2.getLatitude(),
                        "longitude", location2.getLongitude()
                ),
                "distance", String.format("%.2f", distance) +" km",
                "unit", "km"
        );
    }
}
