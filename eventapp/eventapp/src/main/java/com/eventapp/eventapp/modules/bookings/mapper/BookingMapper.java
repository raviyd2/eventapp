package com.eventapp.eventapp.modules.bookings.mapper;

import org.mapstruct.*;

import com.eventapp.eventapp.modules.bookings.dto.BookingDto;
import com.eventapp.eventapp.modules.bookings.model.Booking;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {
    
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "service", ignore = true)
    Booking toEntity(BookingDto bookingDto);
    
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "service.id", target = "serviceId")
    BookingDto toDto(Booking booking);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "service", ignore = true)
    void updateBookingFromDto(BookingDto bookingDto, @MappingTarget Booking booking);
}