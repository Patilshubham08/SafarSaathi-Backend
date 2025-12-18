package com.travel.entities;


import jakarta.persistence.Column;
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
@Table(name="destination")
//@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString

//dest_id,trip_id,name,description,image_url
public class Destinations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long destId;
	
	private Long tripId;
	
	private String destinationName;
	
	@Column(name = "image_url")
    private String imageUrl;
	

}
