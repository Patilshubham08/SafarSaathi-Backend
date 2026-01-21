package com.travel.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.dtos.TripDto; // Import the DTO
import com.travel.entities.Trip;
import com.travel.services.TripService;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:3000")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/{customerId}")
    public ResponseEntity<?> createTrip(
            @RequestBody Trip trip, 
            @PathVariable Long customerId,
            @RequestParam(required = false) Long packageId) {
        try {
            // Service now returns a DTO
            TripDto newTrip = tripService.createTrip(trip, customerId, packageId);
            return ResponseEntity.ok(newTrip);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{tripId}/destinations")
    public ResponseEntity<?> addDestination(@PathVariable Long tripId, @RequestBody com.travel.dtos.DestinationDto destDto) {
        try {
            tripService.addDestination(tripId, destDto);
            return ResponseEntity.ok("Destination added successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{customerId}")
    public List<TripDto> getUserTrips(@PathVariable Long customerId) {
        return tripService.getTripsByCustomer(customerId);
    }
    
    @GetMapping("/details/{tripId}")
    public ResponseEntity<?> getTripDetails(@PathVariable Long tripId) {
        try {
            return ResponseEntity.ok(tripService.getTripById(tripId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        
    }
}