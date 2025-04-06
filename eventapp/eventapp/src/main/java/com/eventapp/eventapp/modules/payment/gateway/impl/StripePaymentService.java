package com.eventapp.eventapp.modules.payment.gateway.impl;

import com.eventapp.eventapp.modules.payment.exception.PaymentException;
import com.eventapp.eventapp.modules.payment.gateway.PaymentGatewayService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StripePaymentService implements PaymentGatewayService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        try {
            // Validate key format
            if (stripeSecretKey == null || !stripeSecretKey.startsWith("sk_test_")) {
                throw new IllegalStateException("Invalid Stripe key. Test keys must start with 'sk_test_'");
            }

            Stripe.apiKey = stripeSecretKey;
            log.info("Stripe initialized with key ending with: {}", 
                stripeSecretKey.substring(Math.max(0, stripeSecretKey.length() - 4)));

        } catch (Exception e) {
            log.error("STRIPE INITIALIZATION FAILED: {}", e.getMessage());
            throw new RuntimeException("Payment system unavailable. Please try again later.");
        }
    }

    @Override
    public String processPayment(String paymentToken, double amount, String currency, String description) {
        try {
            // Validate input parameters
            if (paymentToken == null || paymentToken.isEmpty()) {
                throw new PaymentException("Payment token is required");
            }
            if (amount <= 0) {
                throw new PaymentException("Amount must be positive");
            }
            if (!"inr".equalsIgnoreCase(currency)) {
                throw new PaymentException("Only INR payments are supported");
            }

            // Convert to paise (smallest currency unit)
            long amountInPaise = (long) (amount * 100);
            if (amountInPaise < 50) { // Minimum 0.50 INR
                throw new PaymentException("Minimum payment amount is 0.50 INR");
            }

            ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount(amountInPaise)
                .setCurrency("inr")
                .setSource(paymentToken)
                .setDescription(description)
                .setCapture(true) // Immediately capture payment
                .build();

            Charge charge = Charge.create(params);
            log.info("Payment processed successfully. Charge ID: {}", charge.getId());
            return charge.getId();

        } catch (StripeException e) {
            String errorDetails = e.getStripeError() != null ?
                String.format("Code: %s | Message: %s", e.getCode(), e.getStripeError().getMessage()) :
                e.getMessage();
            
            log.error("STRIPE PAYMENT FAILED: {}", errorDetails);
            throw new PaymentException("Payment failed: " + errorDetails);
        }
    }
}