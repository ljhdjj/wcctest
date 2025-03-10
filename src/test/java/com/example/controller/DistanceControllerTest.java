package com.example.controller;

import com.example.model.Postcode;
import com.example.service.DistanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DistanceController.class, 
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class DistanceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private DistanceService distanceService;  // Use @MockBean to register it in the context

    @Test
    void testGetDistance() throws Exception {
        String postcode1 = "SW1A1AA";
        String postcode2 = "EH11AB";
        
        Postcode loc1 = new Postcode(postcode1, 51.501, -0.141);
        Postcode loc2 = new Postcode(postcode2, 55.953, -3.188);
        double mockDistance = 533.20;
        
        // Set up mocks
        when(distanceService.getPostcodeDetails(postcode1)).thenReturn(loc1);
        when(distanceService.getPostcodeDetails(postcode2)).thenReturn(loc2);
        when(distanceService.calculateDistance(postcode1, postcode2)).thenReturn(mockDistance);
        
        mockMvc.perform(get("/api/distance/{postcode1}/{postcode2}", postcode1, postcode2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location1.postcode").value(postcode1))
                .andExpect(jsonPath("$.location1.latitude").value(51.501))
                .andExpect(jsonPath("$.location1.longitude").value(-0.141))
                .andExpect(jsonPath("$.location2.postcode").value(postcode2))
                .andExpect(jsonPath("$.location2.latitude").value(55.953))
                .andExpect(jsonPath("$.location2.longitude").value(-3.188))
                .andExpect(jsonPath("$.distance").value("533.20 km"))
                .andExpect(jsonPath("$.unit").value("km"));
    }
}
