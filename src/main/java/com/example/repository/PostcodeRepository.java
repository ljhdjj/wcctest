package com.example.repository;

import com.example.model.Postcode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, String> {
    Optional<Postcode> findByPostcode(String postcode);
}

