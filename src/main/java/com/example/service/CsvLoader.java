package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class CsvLoader {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void loadCsvData() {
        try {
            // Read CSV file from resources
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("ukpostcodes.csv");
            if (inputStream == null) {
                throw new RuntimeException("CSV file not found!");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }

                String[] data = line.split(",");
                String postcode = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude = Double.parseDouble(data[2].trim());

                // Insert data into the database
                jdbcTemplate.update("INSERT INTO postcode_table (postcode, latitude, longitude) VALUES (?, ?, ?)",
                        postcode, latitude, longitude);
            }

            System.out.println("CSV Data Loaded Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

