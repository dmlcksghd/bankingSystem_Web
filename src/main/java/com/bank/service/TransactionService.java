package com.bank.service;

import java.util.List;

import com.bank.dao.TransactionDAO;
import com.bank.dto.TransactionDTO;

public class TransactionService {
    private final TransactionDAO transactionDAO = new TransactionDAO();

    // 특정 계좌의 모든 거래 내역 조회
    public List<TransactionDTO> getAllTransactionsByAccountNo(String accountNo) {
        if (accountNo == null || accountNo.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 계좌 번호입니다.");
        }
        return transactionDAO.getTransactionsByAccountNo(accountNo);
    }
    
    // 특정 계좌의 최근 거래 내역 조회
    public List<TransactionDTO> getRecentTransactions(String accountNo, int limit) {
        return transactionDAO.getRecentTransactions(accountNo, limit);
    }

    // 거래 데이터 유효성 검증
    private boolean isValidTransaction(TransactionDTO transaction) {
        return transaction != null &&
               transaction.getAccountNo() != null &&
               !transaction.getAccountNo().isEmpty() &&
               transaction.getAmount() > 0 &&
               (transaction.getType().equalsIgnoreCase("DEPOSIT") ||
                transaction.getType().equalsIgnoreCase("WITHDRAWAL") ||
                transaction.getType().equalsIgnoreCase("TRANSFER"));
    }
}
