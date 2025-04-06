package com.eventapp.eventapp.modules.bookings.model;

public enum BookingStatus {
    PENDING, 
    CONFIRMED, 
    CANCELLED, 
    COMPLETED;
    
    // Optional: Add method to check valid status transitions
    public boolean canTransitionTo(BookingStatus newStatus) {
        switch (this) {
            case PENDING:
                return newStatus == CONFIRMED || newStatus == CANCELLED;
            case CONFIRMED:
                return newStatus == COMPLETED || newStatus == CANCELLED;
            case CANCELLED:
            case COMPLETED:
                return false;
            default:
                throw new IllegalStateException("Unknown booking status");
        }
    }
}