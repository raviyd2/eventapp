package com.eventapp.eventapp.modules.payment.service.impl;

import com.eventapp.eventapp.modules.bookings.model.Booking;
import com.eventapp.eventapp.modules.bookings.model.BookingStatus;
import com.eventapp.eventapp.modules.bookings.repository.BookingRepository;
import com.eventapp.eventapp.modules.payment.dto.PaymentRequest;
import com.eventapp.eventapp.modules.payment.dto.PaymentResponse;
import com.eventapp.eventapp.modules.payment.exception.PaymentException;
import com.eventapp.eventapp.modules.payment.gateway.PaymentGatewayService;
import com.eventapp.eventapp.modules.payment.model.Payment;
import com.eventapp.eventapp.modules.payment.model.PaymentStatus;
import com.eventapp.eventapp.modules.payment.repository.PaymentRepository;
import com.eventapp.eventapp.modules.payment.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentGatewayService paymentGatewayService;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              BookingRepository bookingRepository,
                              PaymentGatewayService paymentGatewayService) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.paymentGatewayService = paymentGatewayService;
    }

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // 1. Find Booking
        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new PaymentException("Booking not found"));

        // 2. Convert to BigDecimal with rounding
        BigDecimal amount = BigDecimal.valueOf(paymentRequest.getAmount())
                .setScale(2, RoundingMode.HALF_UP);

        // 3. Process Payment
        String transactionId = paymentGatewayService.processPayment(
                paymentRequest.getPaymentToken(),
                amount.doubleValue(),
                "inr",
                "Booking #" + booking.getId()
        );

        // 4. Create Payment Record
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setCurrency("INR");
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setTransactionId(transactionId);
        payment.setStatus(PaymentStatus.COMPLETED);

        // 5. Calculate GST
        BigDecimal gstAmount = amount.multiply(new BigDecimal("0.18"))
                .setScale(2, RoundingMode.HALF_UP);
        payment.setGstAmount(gstAmount);

        Payment savedPayment = paymentRepository.save(payment);

        // 6. Update Booking Status
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return mapToPaymentResponse(savedPayment);
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking().getId())
                .amount(payment.getAmount())
                .gstAmount(payment.getGstAmount())
                .currency(payment.getCurrency())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .status(payment.getStatus())
                .build();
    }

    @Override
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .map(this::mapToPaymentResponse)
                .orElseThrow(() -> new PaymentException("Payment not found"));
    }
}