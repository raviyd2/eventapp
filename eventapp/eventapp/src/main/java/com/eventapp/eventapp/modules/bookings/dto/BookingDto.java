package com.eventapp.eventapp.modules.bookings.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.eventapp.eventapp.modules.bookings.model.BookingStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Data
public class BookingDto {
	
    private Long id;
	@NotNull
    private Long userId;
	@NotNull
    private Long serviceId;
	@Future
    private LocalDateTime bookingDate;
    private BookingStatus status; // Make sure this matches the enum package
    private String notes;
}