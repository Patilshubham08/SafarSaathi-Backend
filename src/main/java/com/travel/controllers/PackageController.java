package com.travel.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.dtos.PackageDto;
import com.travel.entities.Packages;
import com.travel.services.PackageService;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "http://localhost:5173")
public class PackageController {

    @Autowired
    private PackageService packageService;

    // 1. Create Package
    @PostMapping("/{vendorId}")
    public ResponseEntity<?> createPackage(@RequestBody Packages pkg, @PathVariable Long vendorId) {
        try {
            PackageDto newPkg = packageService.createPackage(pkg, vendorId);
            return ResponseEntity.ok(newPkg);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Get All Packages
    @GetMapping
    public List<PackageDto> getAllPackages() {
        return packageService.getAllPackages();
    }

    // 👇 3. DELETE PACKAGE (ADD THIS)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id) {
        try {
            packageService.deletePackage(id);
            return ResponseEntity.ok("Package deleted successfully");
        } catch (RuntimeException e) {
            // This returns the "Package not found" message from your ServiceImpl
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}