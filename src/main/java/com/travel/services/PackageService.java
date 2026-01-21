package com.travel.services;

import com.travel.dtos.PackageDto;
import com.travel.entities.Packages;
import java.util.List;

public interface PackageService {
    PackageDto createPackage(Packages pkg, Long vendorId);
    List<PackageDto> getAllPackages();
}