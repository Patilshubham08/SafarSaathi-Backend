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
@CrossOrigin(origins = "http://localhost:3000")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("/{vendorId}")
    public ResponseEntity<?> createPackage(@RequestBody Packages pkg, @PathVariable Long vendorId) {
        try {
            PackageDto newPkg = packageService.createPackage(pkg, vendorId);
            return ResponseEntity.ok(newPkg);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<PackageDto> getAllPackages() {
        return packageService.getAllPackages();
    }
}