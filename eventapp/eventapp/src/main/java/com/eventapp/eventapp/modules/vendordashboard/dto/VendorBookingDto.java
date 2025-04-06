package com.eventapp.eventapp.modules.vendordashboard.dto;

import com.eventapp.eventapp.modules.bookings.model.BookingStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VendorBookingDto {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDateTime bookingDate;
    private BookingStatus status;
    private String notes;
    private LocalDateTime createdAt;
}