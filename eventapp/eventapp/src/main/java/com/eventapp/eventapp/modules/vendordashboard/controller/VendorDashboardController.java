package com.eventapp.eventapp.modules.vendordashboard.controller;

import com.eventapp.eventapp.config.VendorSecurity;
import com.eventapp.eventapp.modules.vendordashboard.dto.VendorBookingDto;
import com.eventapp.eventapp.modules.vendordashboard.dto.VendorServiceDto;
import com.eventapp.eventapp.modules.vendordashboard.service.VendorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorDashboardController {
    private final VendorDashboardService vendorDashboardService;
    private final VendorSecurity vendorSecurity;

    @GetMapping("/{vendorId}/services")
    @PreAuthorize("hasRole('VENDOR') and @vendorSecurity.checkVendorId(authentication, #vendorId)")
    public ResponseEntity<Page<VendorServiceDto>> getVendorServices(
            @PathVariable Long vendorId,
            Pageable pageable) {
        return ResponseEntity.ok(vendorDashboardService.getVendorServices(vendorId, pageable));
    }

    @GetMapping("/{vendorId}/bookings")
    @PreAuthorize("hasRole('VENDOR') and @vendorSecurity.checkVendorId(authentication, #vendorId)")
    public ResponseEntity<Page<VendorBookingDto>> getVendorBookings(
            @PathVariable Long vendorId,
            Pageable pageable) {
        return ResponseEntity.ok(vendorDashboardService.getVendorBookings(vendorId, pageable));
    }
}