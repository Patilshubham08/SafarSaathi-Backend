package com.travel.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="destination")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Destinations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long destId;
	
    // FIX: Link back to Trip
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
	
	private String destinationName;
	
	@Column(name = "image_url")
    private String imageUrl;
}