package com.example.service;

import com.example.model.Postcode;
import com.example.repository.PostcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    @Autowired
    private PostcodeRepository postcodeRepository;

    public Postcode getPostcodeDetails(String postcode) {
        return postcodeRepository.findByPostcode(postcode)
                .orElseThrow(() -> new RuntimeException("Postcode not found: " + postcode));
    }

    public double calculateDistance(String postcode1, String postcode2) {
        Postcode loc1 = getPostcodeDetails(postcode1);
        Postcode loc2 = getPostcodeDetails(postcode2);

        return haversine(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude());
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
