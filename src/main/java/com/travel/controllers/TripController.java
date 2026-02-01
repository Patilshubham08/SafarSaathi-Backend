package com.travel.controllers;

import com.travel.dtos.TripDto;
import com.travel.entities.Trip;
import com.travel.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:5173")
public class TripController {

    @Autowired
    private TripService tripService;

    // ✅ FIXED: Matches axios.post(`.../api/trips/${user.userId}?packageId=${pkg.packageId}`)
    @PostMapping("/{customerId}")
    public ResponseEntity<?> createTrip(
            @PathVariable Long customerId, 
            @RequestParam(required = false) Long packageId,
            @RequestBody Trip trip) {
        try {
            TripDto createdTrip = tripService.createTrip(trip, customerId, packageId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrip);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ FIXED: Matches axios.get(`.../api/trips/customer/${user.userId}`)
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TripDto>> getTripsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(tripService.getTripsByCustomer(customerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long id) {
        try {
            tripService.deleteTrip(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}