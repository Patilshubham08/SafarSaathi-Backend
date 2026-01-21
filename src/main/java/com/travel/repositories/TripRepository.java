package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Trip;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    // Used by TripService to show a customer their history
    List<Trip> findByCustomer_UserId(Long userId);
}