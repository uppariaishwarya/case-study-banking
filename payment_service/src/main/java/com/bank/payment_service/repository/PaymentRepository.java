package com.bank.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.payment_service.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}