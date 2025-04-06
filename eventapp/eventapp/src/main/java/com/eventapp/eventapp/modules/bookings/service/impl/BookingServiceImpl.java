package com.eventapp.eventapp.modules.bookings.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventapp.eventapp.modules.auth.model.User;
import com.eventapp.eventapp.modules.auth.repository.UserRepository;
import com.eventapp.eventapp.modules.bookings.dto.BookingDto;
import com.eventapp.eventapp.modules.bookings.exception.EntityNotFoundException;
import com.eventapp.eventapp.modules.bookings.mapper.BookingMapper;
import com.eventapp.eventapp.modules.bookings.model.Booking;
import com.eventapp.eventapp.modules.bookings.model.BookingStatus;
import com.eventapp.eventapp.modules.bookings.repository.BookingRepository;
import com.eventapp.eventapp.modules.bookings.service.BookingService;
import com.eventapp.eventapp.modules.sevices.model.ServiceEntity;
import com.eventapp.eventapp.modules.sevices.repository.ServiceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.toEntity(bookingDto);
        
        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + bookingDto.getUserId()));
        booking.setUser(user);
        
        ServiceEntity service = serviceRepository.findById(bookingDto.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found with id: " + bookingDto.getServiceId()));
        booking.setService(service);
        service.addBooking(booking);
        
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }
        
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDto> getUserBookings(Long userId, Pageable pageable) {
        return bookingRepository.findByUserId(userId, pageable)
                .map(bookingMapper::toDto);
    }

    @Override
    public BookingDto updateBookingStatus(Long id, BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
        
        if (bookingDto.getStatus() != null) {
            booking.setStatus(bookingDto.getStatus());
        }
        
        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}