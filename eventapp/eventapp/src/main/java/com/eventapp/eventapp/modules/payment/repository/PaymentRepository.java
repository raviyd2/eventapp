package com.eventapp.eventapp.modules.payment.repository;

import com.eventapp.eventapp.modules.payment.model.Payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByBookingId(Long bookingId);
    Optional<Payment> findByTransactionId(String transactionId);
}