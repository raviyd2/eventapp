package com.eventapp.eventapp.modules.vendordashboard.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VendorServiceDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;
    private LocalDateTime createdAt;
}