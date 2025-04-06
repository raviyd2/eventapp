package com.eventapp.eventapp.modules.bookings.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventapp.eventapp.modules.bookings.dto.BookingDto;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto);
    Page<BookingDto> getUserBookings(Long userId, Pageable pageable);
    BookingDto updateBookingStatus(Long id, BookingDto bookingDto);
}