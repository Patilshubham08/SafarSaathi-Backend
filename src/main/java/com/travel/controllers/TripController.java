package com.travel.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.dtos.DestinationDto;
import com.travel.dtos.TripDto; 
import com.travel.entities.Trip;
import com.travel.services.TripService;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:3000")
public class TripController {

    @Autowired
    private TripService tripService;

    // 1. Create Trip
    @PostMapping("/{customerId}")
    public ResponseEntity<?> createTrip(
            @RequestBody Trip trip, 
            @PathVariable Long customerId,
            @RequestParam(required = false) Long packageId) {
        try {
            TripDto newTrip = tripService.createTrip(trip, customerId, packageId);
            return ResponseEntity.ok(newTrip);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // 2. Add Destination
    @PostMapping("/{tripId}/destinations")
    public ResponseEntity<?> addDestination(@PathVariable Long tripId, @RequestBody DestinationDto destDto) {
        try {
            tripService.addDestination(tripId, destDto);
            return ResponseEntity.ok("Destination added successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Get All Trips for Customer
    @GetMapping("/{customerId}")
    public List<TripDto> getUserTrips(@PathVariable Long customerId) {
        return tripService.getTripsByCustomer(customerId);
    }
    
    // 4. Get Single Trip Details
    @GetMapping("/details/{tripId}")
    public ResponseEntity<?> getTripDetails(@PathVariable Long tripId) {
        try {
            return ResponseEntity.ok(tripService.getTripById(tripId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 👇 5. UPDATE TRIP (Added this new method) 👇
    @PutMapping("/{tripId}")
    public ResponseEntity<?> updateTrip(@PathVariable Long tripId, @RequestBody TripDto tripDto) {
        try {
            TripDto updatedTrip = tripService.updateTrip(tripId, tripDto);
            return ResponseEntity.ok(updatedTrip);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}