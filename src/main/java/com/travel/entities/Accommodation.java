package com.travel.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="accommodation")
//@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
//Accommodation: accom_id,trip_id,hotel_name,address,price,check_in
public class Accommodation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accomId;
	
	private Long tripId;
	
	private String hotelName;
	
	private String address;
	
	private Double price;
	
	private LocalDateTime checkIn;
	

}
