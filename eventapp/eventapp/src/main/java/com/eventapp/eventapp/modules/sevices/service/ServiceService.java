package com.eventapp.eventapp.modules.sevices.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.eventapp.eventapp.modules.sevices.dto.ServiceDto;

public interface ServiceService {
	@Transactional
    ServiceDto createService(ServiceDto serviceDto);
	
	@Transactional(readOnly = true)
    ServiceDto getServiceById(Long id);
	
	@Transactional(readOnly = true)
    Page<ServiceDto> getAllServices(String category, Double minPrice, Double maxPrice, Pageable pageable);
	
	@Transactional(readOnly = true)
    Page<ServiceDto> getVendorServices(Long vendorId, Pageable pageable);
	
	@Transactional
    ServiceDto updateService(Long id, ServiceDto serviceDto);
	
	@Transactional
    void deleteService(Long id);
}