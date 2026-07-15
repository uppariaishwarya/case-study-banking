package com.bank.transaction_service.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bank.transaction_service.entity.Transaction;
import com.bank.transaction_service.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    public String transfer(Long from, Long to, double amount) {

        // ✅ STEP 1: Validations
        if (amount <= 0) {
            return "Invalid Amount ❌";
        }

        if (from.equals(to)) {
            return "Cannot transfer to same account ❌";
        }

        try {

            // ✅ CALL PAYMENT SERVICE (NEW LOGIC ✅)
            Map<String, Object> request = new HashMap<>();
            request.put("fromAccount", from);
            request.put("toAccount", to);
            request.put("amount", amount);

            String paymentStatus = restTemplate.postForObject(
                    "http://PAYMENT-SERVICE/payments/process",
                    request,
                    String.class
            );

            // ✅ SAVE SUCCESS
            Transaction txn = new Transaction();
            txn.setFromAccount(from);
            txn.setToAccount(to);
            txn.setAmount(amount);
            txn.setStatus(paymentStatus);
            txn.setDate(LocalDateTime.now());

            repo.save(txn);

            return "Transfer " + paymentStatus + " ✅";

        } catch (Exception e) {

            // ✅ SAVE FAILED
            Transaction txn = new Transaction();
            txn.setFromAccount(from);
            txn.setToAccount(to);
            txn.setAmount(amount);
            txn.setStatus("FAILED");
            txn.setDate(LocalDateTime.now());

            repo.save(txn);

            return "Transaction Failed ❌";
        }
    
    }

	public List<Transaction> getTransactions(Long accountId) {
    return repo.findByFromAccountOrToAccount(accountId, accountId);
}
	}
