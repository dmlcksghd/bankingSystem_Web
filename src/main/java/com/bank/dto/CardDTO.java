package com.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private int cardNo;
    private String accountNo;
    private double limitAmount; // 카드 한도
    private String issuedAt; // 발급 날짜
}