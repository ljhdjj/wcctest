package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.PostalCodeDTO;
import com.example.model.Postcode;
import com.example.repository.PostcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/postcodes")
public class PostalCodeController {

    @Autowired
    private PostcodeRepository postcodeRepository;

    @GetMapping
    public ResponseEntity<List<PostalCodeDTO>> getAllPostcodes() {
        List<PostalCodeDTO> list = postcodeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{postcode}")
    public ResponseEntity<?> getPostcode(@PathVariable String postcode) {
        Optional<Postcode> opt = postcodeRepository.findById(postcode);
        if (opt.isPresent()) {
            return ResponseEntity.ok(convertToDTO(opt.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("error", "Postal code not found", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createPostcode(@RequestBody PostalCodeDTO dto) {
        if (postcodeRepository.existsById(dto.getPostcode())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("error", "Postal code already exists", null));
        }
        Postcode newMapping = new Postcode(dto.getPostcode(), dto.getLatitude(), dto.getLongitude());
        Postcode saved = postcodeRepository.save(newMapping);
        return ResponseEntity.ok(new ApiResponse("success", "Mapping created", convertToDTO(saved)));
    }

    @PutMapping("/{postcode}")
    public ResponseEntity<ApiResponse> updatePostcode(@PathVariable String postcode, @RequestBody PostalCodeDTO dto) {
        Optional<Postcode> opt = postcodeRepository.findById(postcode);
        if (opt.isPresent()) {
            Postcode existing = opt.get();
            existing.setLatitude(dto.getLatitude());
            existing.setLongitude(dto.getLongitude());
            Postcode updated = postcodeRepository.save(existing);
            return ResponseEntity.ok(new ApiResponse("success", "Mapping updated", convertToDTO(updated)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("error", "Postal code not found", null));
        }
    }

    @DeleteMapping("/{postcode}")
    public ResponseEntity<ApiResponse> deletePostcode(@PathVariable String postcode) {
        if (postcodeRepository.existsById(postcode)) {
            postcodeRepository.deleteById(postcode);
            return ResponseEntity.ok(new ApiResponse("success", "Mapping deleted", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("error", "Postal code not found", null));
        }
    }

    private PostalCodeDTO convertToDTO(Postcode entity) {
        return new PostalCodeDTO(entity.getPostcode(), entity.getLatitude(), entity.getLongitude());
    }
}
