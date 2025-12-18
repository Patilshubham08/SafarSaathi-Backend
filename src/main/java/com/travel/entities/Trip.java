package com.travel.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private TripStatus tripStatus;
	
	
	@ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = true)
    private Package selectedPackage;
	

}
