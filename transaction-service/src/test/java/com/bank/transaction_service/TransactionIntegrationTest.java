package com.bank.transaction_service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.bank.transaction_service.controller.TransactionController;

@SpringBootTest   // ✅ Loads FULL application
@AutoConfigureMockMvc
@ActiveProfiles("test")   // ✅ ADD THIS

public class TransactionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testTransferIntegration() throws Exception {

        String json = """
        {
          "fromAccount": 1,
          "toAccount": 2,
          "amount": 100
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
