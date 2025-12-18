package com.travel.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="bookings")
@Getter
@Setter
@ToString
@RequiredArgsConstructor


//Bookings: booking_id, trip_id, status, date
public class Bookings {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookingId;
	
	private Long tripId;
	
	private LocalDate bookingDate;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus bookingsStatus;
	
}
