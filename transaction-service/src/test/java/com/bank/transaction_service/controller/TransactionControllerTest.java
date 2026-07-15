package com.bank.transaction_service.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.transaction_service.service.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Test
    void testTransferAPI() throws Exception {

        Mockito.when(service.transfer(1L, 2L, 200))
               .thenReturn("Transfer Successful ✅");

        String json = """
        {
          "fromAccount": 1,
          "toAccount": 2,
          "amount": 200
        }
        """;

        mockMvc.perform(post("/transactions/transfer")
                .with(user("testUser"))
                .with(csrf())
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());
    }
}

