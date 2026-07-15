package com.bank.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.bank.payment_service.dto.PaymentRequest;
import com.bank.payment_service.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/process")
    public String process(@Valid @RequestBody PaymentRequest request) {

        return service.processPayment(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );
    }
}