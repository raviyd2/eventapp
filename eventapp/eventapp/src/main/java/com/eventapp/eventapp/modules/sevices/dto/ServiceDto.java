package com.eventapp.eventapp.modules.sevices.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ServiceDto {
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
    
    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private double price;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Vendor ID is required")
    private Long vendorId;
    
 // In Service entity, consider changing to:
    private boolean available = true; // Note: Not isAvailable (for proper JSON serialization)
}