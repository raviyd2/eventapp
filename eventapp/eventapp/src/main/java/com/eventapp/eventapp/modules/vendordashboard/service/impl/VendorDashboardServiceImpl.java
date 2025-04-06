package com.eventapp.eventapp.modules.vendordashboard.service.impl;

import com.eventapp.eventapp.modules.auth.repository.UserRepository;
import com.eventapp.eventapp.modules.bookings.model.Booking;
import com.eventapp.eventapp.modules.bookings.repository.BookingRepository;
import com.eventapp.eventapp.modules.sevices.repository.ServiceRepository;
import com.eventapp.eventapp.modules.vendordashboard.dto.VendorBookingDto;
import com.eventapp.eventapp.modules.vendordashboard.dto.VendorServiceDto;
import com.eventapp.eventapp.modules.vendordashboard.service.VendorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorDashboardServiceImpl implements VendorDashboardService {
    private final ServiceRepository serviceRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Override
    public Page<VendorServiceDto> getVendorServices(Long vendorId, Pageable pageable) {
        return serviceRepository.findByVendorId(vendorId, pageable)
                .map(service -> {
                    VendorServiceDto dto = new VendorServiceDto();
                    dto.setId(service.getId());
                    dto.setName(service.getName());
                    dto.setDescription(service.getDescription());
                    dto.setPrice(service.getPrice());
                    dto.setCategory(service.getCategory());
                    dto.setAvailable(service.isAvailable());
                    dto.setCreatedAt(service.getCreatedAt());
                    return dto;
                });
    }

    @Override
    public Page<VendorBookingDto> getVendorBookings(Long vendorId, Pageable pageable) {
        return bookingRepository.findByServiceVendorId(vendorId, pageable)
                .map(booking -> {
                    VendorBookingDto dto = new VendorBookingDto();
                    dto.setId(booking.getId());
                    dto.setUserId(booking.getUser().getId());
                    dto.setUserName(booking.getUser().getName());
                    dto.setUserEmail(booking.getUser().getEmail());
                    dto.setBookingDate(booking.getBookingDate());
                    dto.setStatus(booking.getStatus());
                    dto.setNotes(booking.getNotes());
                    dto.setCreatedAt(booking.getCreatedAt());
                    return dto;
                });
    }
}