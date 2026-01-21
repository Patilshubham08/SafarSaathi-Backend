package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Destinations;
import java.util.List;

public interface DestinationsRepository extends JpaRepository<Destinations, Long> {
    // Find all destinations for a specific trip (Useful for showing the itinerary)
    List<Destinations> findByTrip_TripId(Long tripId);
}