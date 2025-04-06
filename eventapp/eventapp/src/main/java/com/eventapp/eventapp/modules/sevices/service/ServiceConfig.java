package com.eventapp.eventapp.modules.sevices.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eventapp.eventapp.modules.auth.repository.UserRepository;
import com.eventapp.eventapp.modules.sevices.mapper.ServiceMapper;
import com.eventapp.eventapp.modules.sevices.repository.ServiceRepository;
import com.eventapp.eventapp.modules.sevices.service.impl.ServiceServiceImpl;

@Configuration
public class ServiceConfig {
    
    @Bean
    public ServiceService serviceService(
        ServiceRepository serviceRepository,
        UserRepository userRepository,
        ServiceMapper serviceMapper) {
        return new ServiceServiceImpl(userRepository, serviceRepository, serviceMapper);
    }
}
