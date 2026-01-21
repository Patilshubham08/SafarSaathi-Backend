package com.travel.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="accommodation")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Accommodation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accomId;
	
    // FIX: Link back to Trip
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
	
	private String hotelName;
	private String address;
	private Double price;
	private LocalDateTime checkIn;
}