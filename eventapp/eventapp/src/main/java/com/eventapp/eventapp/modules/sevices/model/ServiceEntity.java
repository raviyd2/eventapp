package com.eventapp.eventapp.modules.sevices.model;

import com.eventapp.eventapp.modules.auth.model.User;
import com.eventapp.eventapp.modules.bookings.model.Booking;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "services")
@EntityListeners(AuditingEntityListener.class) // Add this annotation
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT", length = 500)
    private String description;
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private String category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private User vendor;
    
    @Column(nullable = false, name = "is_available")
    private boolean available = true;
    
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // Helper method to maintain bidirectional relationship
    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setService(this);
    }
}