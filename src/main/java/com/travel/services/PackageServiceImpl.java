package com.travel.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.dtos.PackageDto;
import com.travel.entities.Packages;
import com.travel.entities.User;
import com.travel.repositories.PackagesRepository;
import com.travel.repositories.UserRepository;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackagesRepository packageRepo;
    
    @Autowired
    private UserRepository userRepo;

    // --- Helper: Convert Entity to DTO ---
    private PackageDto mapToDto(Packages pkg) {
        PackageDto dto = new PackageDto();
        dto.setPackageId(pkg.getPackageId());
        dto.setPackageName(pkg.getPackageName());
        dto.setDescription(pkg.getDescription());
        dto.setPrice(pkg.getPrice());
        dto.setImageUrl(pkg.getImageUrl());
        
        if (pkg.getVendor() != null) {
            dto.setVendorId(pkg.getVendor().getUserId());
            dto.setVendorName(pkg.getVendor().getName());
        }
        return dto;
    }

    @Override
    public PackageDto createPackage(Packages pkg, Long vendorId) {
        User vendor = userRepo.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        
        pkg.setVendor(vendor);
        Packages savedPkg = packageRepo.save(pkg);
        
        return mapToDto(savedPkg); // Return DTO
    }

    @Override
    public List<PackageDto> getAllPackages() {
        return packageRepo.findAll()
                .stream()
                .map(this::mapToDto) // Convert list to DTOs
                .collect(Collectors.toList());
    }
}