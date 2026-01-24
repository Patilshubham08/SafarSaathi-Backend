package com.travel.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore; // 1. IMPORT THIS

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="packages")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Packages {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long packageId;
	private String packageName;
	private Double price;
	private String description;
	
	@Column(name = "image_url")
    private String imageUrl;
	
	@ManyToOne
    @JoinColumn(name = "vendor_id")
    @JsonIgnore // (Optional but Recommended) Prevents loops back to Vendor
    private User vendor;

    // 👇 THIS IS THE FIX 👇
    @OneToMany(mappedBy = "selectedPackage", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore // <--- ADD THIS to stop the "LazyInitializationException"
    private List<Trip> trips = new ArrayList<>();

}