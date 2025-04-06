package com.eventapp.eventapp.modules.sevices.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventapp.eventapp.modules.auth.model.User;
import com.eventapp.eventapp.modules.auth.repository.UserRepository;
import com.eventapp.eventapp.modules.sevices.dto.ServiceDto;
import com.eventapp.eventapp.modules.sevices.exception.ResourceNotFoundException;
import com.eventapp.eventapp.modules.sevices.mapper.ServiceMapper;
import com.eventapp.eventapp.modules.sevices.model.ServiceEntity;
import com.eventapp.eventapp.modules.sevices.repository.ServiceRepository;
import com.eventapp.eventapp.modules.sevices.service.ServiceService;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {
    
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    
    public ServiceServiceImpl(
            UserRepository userRepository, 
            ServiceRepository serviceRepository, 
            ServiceMapper serviceMapper) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    @Transactional
    public ServiceDto createService(ServiceDto serviceDto) {
        User vendor = userRepository.findById(serviceDto.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + serviceDto.getVendorId()));
        
        ServiceEntity serviceEntity = serviceMapper.toEntity(serviceDto);
        serviceEntity.setVendor(vendor);
        
        ServiceEntity savedService = serviceRepository.save(serviceEntity);
        return serviceMapper.toDto(savedService);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceDto getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(serviceMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceDto> getAllServices(String category, Double minPrice, Double maxPrice, Pageable pageable) {
        if (category != null || minPrice != null || maxPrice != null) {
            return serviceRepository.findByFilters(category, minPrice, maxPrice, pageable)
                    .map(serviceMapper::toDto);
        }
        return serviceRepository.findAll(pageable)
                .map(serviceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceDto> getVendorServices(Long vendorId, Pageable pageable) {
        if (!userRepository.existsById(vendorId)) {
            throw new ResourceNotFoundException("Vendor not found with id: " + vendorId);
        }
        return serviceRepository.findByVendorId(vendorId, pageable)
                .map(serviceMapper::toDto);
    }

    @Override
    @Transactional
    public ServiceDto updateService(Long id, ServiceDto serviceDto) {
        return serviceRepository.findById(id)
                .map(existingService -> {
                    serviceMapper.updateEntity(serviceDto, existingService);
                    if (serviceDto.getVendorId() != null && 
                        !existingService.getVendor().getId().equals(serviceDto.getVendorId())) {
                        User newVendor = userRepository.findById(serviceDto.getVendorId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Vendor not found with id: " + serviceDto.getVendorId()));
                        existingService.setVendor(newVendor);
                    }
                    ServiceEntity updatedService = serviceRepository.save(existingService);
                    return serviceMapper.toDto(updatedService);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
    }
}