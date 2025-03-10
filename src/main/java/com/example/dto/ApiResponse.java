package com.example.dto;

public class ApiResponse {
    private String status;
    private String message;
    private PostalCodeDTO data;

    public ApiResponse() {}

    public ApiResponse(String status, String message, PostalCodeDTO data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PostalCodeDTO getData() {
        return data;
    }

    public void setData(PostalCodeDTO data) {
        this.data = data;
    }
}
