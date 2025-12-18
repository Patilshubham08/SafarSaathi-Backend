package com.travel.entities;

import java.util.ArrayList;
import java.util.List;

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



//Packages: pkg_id, name, price, vendor_id, image_url
public class Packages {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long packageId;
	private String packageName;
	private Double price;
	private String description;
	//private Long vendorId;
	 
	@Column(name = "image_url")
    private String imageUrl;
	
	@ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;

    // SAFETY: If a package is deleted, we don't necessarily want to delete the user's trip history.
    // So we use PERSIST + MERGE here too.
    @OneToMany(mappedBy = "selectedPackage", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Trip> trips = new ArrayList<>();
	


}
