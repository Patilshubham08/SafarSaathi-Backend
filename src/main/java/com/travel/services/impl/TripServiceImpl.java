package com.travel.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dtos.DestinationDto;
import com.travel.dtos.TripDto;
import com.travel.entities.Destinations;
import com.travel.entities.Packages;
import com.travel.entities.Trip;
import com.travel.entities.TripStatus;
import com.travel.entities.User;
import com.travel.repositories.DestinationsRepository;
import com.travel.repositories.PackagesRepository;
import com.travel.repositories.TripRepository;
import com.travel.repositories.UserRepository;
import com.travel.services.TripService;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PackagesRepository packageRepo;

    // --- 1. Helper Method to Convert Entity -> DTO ---
    private TripDto mapToDto(Trip trip) {
        TripDto dto = new TripDto();
        dto.setTripId(trip.getTripId());
        dto.setTripName(trip.getTripName());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setBudget(trip.getBudget());
        dto.setTripStatus(trip.getTripStatus());
        
        // Handle Customer
        if (trip.getCustomer() != null) {
            dto.setCustomerId(trip.getCustomer().getUserId());
        }
        
        // Handle Package (if exists)
        if (trip.getSelectedPackage() != null) {
            dto.setPackageName(trip.getSelectedPackage().getPackageName());
        }
        return dto;
    }

    // --- 2. Create Trip ---
    @Override
    public TripDto createTrip(Trip trip, Long customerId, Long packageId) {
        User customer = userRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        trip.setCustomer(customer);

        if (packageId != null) {
            Packages selectedPackage = packageRepo.findById(packageId)
                    .orElseThrow(() -> new RuntimeException("Package not found"));
            trip.setSelectedPackage(selectedPackage);
            if(trip.getBudget() == null) trip.setBudget(selectedPackage.getPrice());
        }

        trip.setTripStatus(TripStatus.SCHEDULED);
        
        // Save Entity
        Trip savedTrip = tripRepo.save(trip);
        
        // Return DTO
        return mapToDto(savedTrip);
    }

    // --- 3. Get All Trips (Returns List of DTOs) ---
    @Override
    public List<TripDto> getTripsByCustomer(Long customerId) {
        List<Trip> trips = tripRepo.findByCustomer_UserId(customerId);
        // Convert the whole list to DTOs
        return trips.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // --- 4. Get Single Trip ---
    @Override
    public TripDto getTripById(Long tripId) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        return mapToDto(trip);
    }

    @Autowired
    private DestinationsRepository destRepo; // Short and clean

    @Override
    public void addDestination(Long tripId, DestinationDto destDto) {
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        Destinations dest = new Destinations(); // Clean name
        dest.setDestinationName(destDto.getDestinationName());
        dest.setImageUrl(destDto.getImageUrl());
        
        dest.setTrip(trip);
        
        destRepo.save(dest);
    }

    @Override
    public TripDto updateTrip(Long tripId, TripDto tripDto) {
        // 1. Find the existing trip
        Trip trip = tripRepo.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));

        // 2. Partial Update: Only update fields that the user actually sent
        if (tripDto.getTripName() != null) {
            trip.setTripName(tripDto.getTripName());
        }
        if (tripDto.getStartDate() != null) {
            trip.setStartDate(tripDto.getStartDate());
        }
        if (tripDto.getEndDate() != null) {
            trip.setEndDate(tripDto.getEndDate());
        }
        if (tripDto.getBudget() != null) {
            trip.setBudget(tripDto.getBudget());
        }
        if (tripDto.getTripStatus() != null) {
            trip.setTripStatus(tripDto.getTripStatus());
        }

        // 3. Save the changes to the database
        Trip updatedTrip = tripRepo.save(trip);

        // 4. Convert back to DTO and return
        return mapToDto(updatedTrip);
    }
}