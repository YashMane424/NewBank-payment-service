package com.newbank.paymentservice.service;

import com.newbank.paymentservice.entity.Payment;
import com.newbank.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {
        log.info("Creating payment for userId: {}", payment.getUserId());
        payment.setPaymentId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
}
