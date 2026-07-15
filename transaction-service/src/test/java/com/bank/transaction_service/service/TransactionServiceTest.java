package com.bank.transaction_service.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.bank.transaction_service.repository.TransactionRepository;
import com.bank.transaction_service.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;

    @Mock
    private TransactionRepository repo;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void testInvalidAmount() {
        String result = service.transfer(1L, 2L, 0);
        assertEquals("Invalid Amount ❌", result);
    }

    @Test
    void testSameAccount() {
        String result = service.transfer(1L, 1L, 100);
        assertEquals("Cannot transfer to same account ❌", result);
    }
}