package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	private String accountNo;
    private int customerId;
    private double balance;
    private String status;
    private String createdAt;
    private String updatedAt;
}
