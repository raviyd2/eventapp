package com.eventapp.eventapp.modules.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor")
@PreAuthorize("hasRole('VENDOR')")
public class VendorController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> vendorDashboard() {
        return ResponseEntity.ok("Welcome to Vendor Dashboard!");
    }
}
