package com.eventapp.eventapp.modules.payment.gateway;

public interface PaymentGatewayService {
    String processPayment(String paymentToken, double amount, String currency, String description);
}