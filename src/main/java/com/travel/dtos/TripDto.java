package com.travel.dtos;

import java.time.LocalDate;
import com.travel.entities.TripStatus;
import lombok.Data;

@Data
public class TripDto {
    private Long tripId;
    private String tripName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private TripStatus tripStatus;
    
    // Instead of the full objects, we just store IDs or Names
    private Long customerId;
    private String packageName; 
}