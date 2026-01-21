package com.travel.entities;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user")
//@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString

//Users: user_id, name, email, role, password. 
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	
	@Column(nullable = false)
	 private String name;
	
	@Column(nullable = false, unique = true)
	 private String email;
	 
	@Column(nullable = false)
	 private String password;
	 
	 @Enumerated(EnumType.STRING)
	 private UserRole userRole;
	 
	// SAFETY ON: Prevents deleting a Vendor if they have active Packages
	    @OneToMany(mappedBy = "vendor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	    private List<Packages> packages = new ArrayList<>();

	    // SAFETY ON: Prevents deleting a Customer if they have active Trips
	    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	    private List<Trip> trips = new ArrayList<>();
	 
	 
	 
	 
    
	 
}
