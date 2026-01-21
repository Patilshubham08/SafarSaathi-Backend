package com.travel.services;

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
}