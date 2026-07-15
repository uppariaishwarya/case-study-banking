package com.bank.transaction_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.transaction_service.dto.TransactionRequest;
import com.bank.transaction_service.entity.Transaction;
import com.bank.transaction_service.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;
 
    @PostMapping("/transfer")
    public String transfer(@Valid @RequestBody TransactionRequest request) {

        return service.transfer(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );
    }
    @GetMapping("/id/{accountId}")
    public List<Transaction> getTransactions(@PathVariable Long accountId) {
        return service.getTransactions(accountId);  // ✅ CORRECT
    }
    @GetMapping("/test-user")
    public String test(@RequestHeader("X-User") String user) {
    	System.out.println("TEST API HIT ✅");
        return "Hello " + user;
    }
    @GetMapping("/admin")
    public String adminAccess(@RequestHeader("X-Role") String role) {

        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Access Denied ❌");
        }

        return "Welcome Admin ✅";
    }

}

