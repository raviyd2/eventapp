package com.eventapp.eventapp.modules.bookings.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventapp.eventapp.modules.bookings.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    Page<Booking> findByUserId(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT b FROM Booking b JOIN b.service s WHERE s.vendor.id = :vendorId")
    Page<Booking> findByServiceVendorId(@Param("vendorId") Long vendorId, Pageable pageable);
    
    // Add this method for finding bookings by service ID if needed
    Page<Booking> findByServiceId(Long serviceId, Pageable pageable);
}