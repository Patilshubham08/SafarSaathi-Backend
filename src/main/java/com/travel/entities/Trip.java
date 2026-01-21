package com.travel.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="trip")
@Getter
@Setter
@ToString
@RequiredArgsConstructor



//Trips: trip_id, customer_id, package_id, name, status.

public class Trip {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tripId;
	
//	private Long customerId;
//	private Long packageId;
	
	
	private String tripName;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double budget;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TripStatus tripStatus;
	
	
	@ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = true)
    private Packages selectedPackage;
    
    
 // Booking is vital. If Trip is deleted, Booking should be deleted (or archived).
    @OneToOne(mappedBy = "trip", cascade = CascadeType.ALL)
    private Bookings booking;

    // CLEANUP ON: Deleting a Trip automatically deletes its flights/hotels
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transportation> transportations = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accommodation> accommodations = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destinations> destinations = new ArrayList<>();
    
    
    
    
    
	

}
