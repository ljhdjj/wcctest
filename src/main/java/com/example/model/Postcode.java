package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "postcode_table")
public class Postcode {
    @Id
    private String postcode;
    private double latitude;
    private double longitude;

	public Postcode() {}

	public Postcode(String postcode, double latitude, double longitude) {
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
	
    // Getters and Setters
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
