package com.eventapp.eventapp.modules.payment.service;

import com.eventapp.eventapp.modules.payment.dto.PaymentRequest;
import com.eventapp.eventapp.modules.payment.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentByTransactionId(String transactionId);
}