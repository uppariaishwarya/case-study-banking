package com.bank.payment_service.dto;

import jakarta.validation.constraints.*;

public class PaymentRequest {

    @NotNull(message = "From account required")
    private Long fromAccount;

    @NotNull(message = "To account required")
    private Long toAccount;

    @Positive(message = "Amount must be > 0")
    private double amount;

	public Long getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Long fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Long getToAccount() {
		return toAccount;
	}

	public void setToAccount(Long toAccount) {
		this.toAccount = toAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

    // getters & setters
    
}
