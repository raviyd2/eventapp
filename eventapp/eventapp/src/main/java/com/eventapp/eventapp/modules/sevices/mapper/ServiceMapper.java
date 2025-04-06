package com.eventapp.eventapp.modules.sevices.mapper;

import org.mapstruct.*;
import com.eventapp.eventapp.modules.auth.model.User;
import com.eventapp.eventapp.modules.sevices.dto.ServiceDto;
import com.eventapp.eventapp.modules.sevices.model.ServiceEntity;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(target = "vendor", ignore = true)
    ServiceEntity toEntity(ServiceDto serviceDto);

    @Mapping(target = "vendorId", source = "vendor.id")
    ServiceDto toDto(ServiceEntity service);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vendor", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ServiceDto serviceDto, @MappingTarget ServiceEntity service);

    default User mapVendorIdToUser(Long vendorId) {
        if (vendorId == null) return null;
        User user = new User();
        user.setId(vendorId);
        return user;
    }
}