package com.travel.dtos;

import lombok.Data;

@Data
public class PackageDto {
    private Long packageId;
    private String packageName;
    private String description;
    private Double price;
    private String imageUrl;
    
    // Instead of the full 'User' object, we just send the ID and Name
    private Long vendorId;
    private String vendorName;
}