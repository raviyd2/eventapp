package com.eventapp.eventapp.modules.vendordashboard.service;

import com.eventapp.eventapp.modules.vendordashboard.dto.VendorBookingDto;
import com.eventapp.eventapp.modules.vendordashboard.dto.VendorServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorDashboardService {
    Page<VendorServiceDto> getVendorServices(Long vendorId, Pageable pageable);
    Page<VendorBookingDto> getVendorBookings(Long vendorId, Pageable pageable);
}