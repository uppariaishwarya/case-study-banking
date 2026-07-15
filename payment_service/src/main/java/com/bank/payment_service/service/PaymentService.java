package com.bank.payment_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bank.payment_service.entity.Payment;
import com.bank.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    public String processPayment(Long fromAccount, Long toAccount, double amount) {

        // ✅ Create payment record
        Payment payment = new Payment();
        payment.setFromAccount(fromAccount);
        payment.setToAccount(toAccount);
        payment.setAmount(amount);
        payment.setStatus("PENDING");

        repo.save(payment);

        try {
            // ✅ Call account-service (debit/credit)
            restTemplate.postForObject(
                    "http://ACCOUNT-SERVICE/accounts/transfer",
                    payment,
                    String.class
            );

            payment.setStatus("SUCCESS");

        } catch (Exception e) {
            payment.setStatus("FAILED");
        }

        repo.save(payment);

        return payment.getStatus();
    }
}