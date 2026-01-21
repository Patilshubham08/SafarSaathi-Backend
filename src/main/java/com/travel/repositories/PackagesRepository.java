package com.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entities.Packages;
import java.util.List;

public interface PackagesRepository extends JpaRepository<Packages, Long> {
    // Used by PackageService to find packages
    List<Packages> findByVendor_UserId(Long vendorId);
}