package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Integer transactionId;
    private String accountNo;
    private Double amount;
    private String type; // DEPOSIT, WITHDRAWAL, TRANSFER
    private String transactionDate;
}