package com.travel.services.impl;

import com.travel.dtos.DestinationDto;
import com.travel.dtos.TripDto;
import com.travel.entities.Trip;
import com.travel.entities.User;
import com.travel.entities.Packages;
import com.travel.entities.TripStatus; // Ensure this is imported
import com.travel.repositories.TripRepository;
import com.travel.repositories.UserRepository;
import com.travel.repositories.PackagesRepository;
import com.travel.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PackagesRepository packagesRepository;

    @Override
    public TripDto createTrip(Trip trip, Long customerId, Long packageId) {
        // 1. Fetch Customer
        User user = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        trip.setCustomer(user);

        // 2. Fetch and Link Package
        if (packageId != null) {
            Packages pkg = packagesRepository.findById(packageId)
                    .orElseThrow(() -> new RuntimeException("Package not found with ID: " + packageId));
            trip.setSelectedPackage(pkg);
            
            // Auto-fill trip info from the selected package if not already set
            if (trip.getTripName() == null) trip.setTripName(pkg.getPackageName());
            if (trip.getBudget() == null) trip.setBudget(pkg.getPrice());
        }

        // 3. Safety: Default Status if missing
        if (trip.getTripStatus() == null) {
            trip.setTripStatus(TripStatus.SCHEDULED);
        }

        // 4. Basic Date Validation
        if (trip.getStartDate() != null && trip.getEndDate() != null) {
            if (trip.getEndDate().isBefore(trip.getStartDate())) {
                throw new RuntimeException("End date cannot be before start date.");
            }
        }

        Trip savedTrip = tripRepository.save(trip);
        return mapToDto(savedTrip);
    }

    @Override
    public List<TripDto> getTripsByCustomer(Long customerId) {
        return tripRepository.findByCustomerUserId(customerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TripDto getTripById(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
        return mapToDto(trip);
    }

    @Override
    public TripDto updateTrip(Long tripId, TripDto dto) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        trip.setTripName(dto.getTripName());
        trip.setBudget(dto.getBudget());
        trip.setTripStatus(dto.getTripStatus());
        trip.setStartDate(dto.getStartDate());
        trip.setEndDate(dto.getEndDate());
        
        return mapToDto(tripRepository.save(trip));
    }

    @Override
    public void deleteTrip(Long tripId) {
        // 1. Log the incoming ID to identify 'undefined' or 'null' issues from frontend
        System.out.println("DEBUG: Attempting to delete Trip with ID: " + tripId);

        // 2. Use findById instead of existsById for more detailed error reporting
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Cannot delete: Trip not found with ID: " + tripId));

        // 3. Perform the delete
        try {
            tripRepository.delete(trip);
            System.out.println("DEBUG: Trip " + tripId + " deleted successfully.");
        } catch (Exception e) {
            // This catches Foreign Key constraints (e.g., if bookings are linked)
            throw new RuntimeException("Database error during deletion: " + e.getMessage());
        }
    }

    @Override
    public void addDestination(Long tripId, DestinationDto destDto) {
        // Implementation logic for destinations
    }

    // ✅ REFINED MAPPING: Prevents NullPointerExceptions
    private TripDto mapToDto(Trip trip) {
        TripDto dto = new TripDto();
        dto.setTripId(trip.getTripId());
        dto.setTripName(trip.getTripName());
        dto.setBudget(trip.getBudget());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setTripStatus(trip.getTripStatus());
        
        if (trip.getCustomer() != null) {
            dto.setCustomerId(trip.getCustomer().getUserId());
        }
        
        if (trip.getSelectedPackage() != null) {
            dto.setPackageName(trip.getSelectedPackage().getPackageName());
        } else {
            dto.setPackageName("Custom Trip");
        }
        return dto;
    }
}