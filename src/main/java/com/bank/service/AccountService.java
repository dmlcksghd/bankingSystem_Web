package com.bank.service;

import java.util.List;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;

public class AccountService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    

    // 생성자
    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // 특정 고객의 모든 계좌 조회
    public List<AccountDTO> getAccountsByCustomerId(int customerId) {

        List<AccountDTO> accounts = accountDAO.getAccountsByCustomerId(customerId);
 
        return accounts;
    }



    // 계좌 잔액 업데이트
    public boolean updateBalance(String accountNo, double newBalance) {
        if (accountNo == null || accountNo.isEmpty()) {
            throw new IllegalArgumentException("계좌 번호는 null이거나 비어 있을 수 없습니다.");
        }
        if (newBalance < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        return accountDAO.updateBalance(accountNo, newBalance);
    }

    // 계좌 상태 변경
    public boolean updateAccountStatus(String accountNo, String status) {
    	if (accountNo == null || accountNo.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 계좌 번호입니다.");
        }
    	
        // 상태 값이 null인 경우 기본값 설정
    	if (status == null || status.isEmpty()) {
            status = "INACTIVE";
        }
    	
    	// 계좌 폐쇄상태에서는 변경 불가
    	if (!(status.equalsIgnoreCase("ACTIVE") || status.equalsIgnoreCase("INACTIVE"))) {
            throw new IllegalArgumentException("변경할 수 없는 계좌 상태입니다.");
        }
    	
        return accountDAO.updateAccountStatus(accountNo, status);
    }
    
    // 송금 로직
    public boolean transferAmount(String fromAccountNo, String toAccountNo, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("송금 금액은 0보다 커야 합니다.");
        }

        // 잔액 업데이트
        boolean sender = accountDAO.updateBalance(fromAccountNo, -amount);
        boolean reciever = accountDAO.updateBalance(toAccountNo, amount);

        if (sender && reciever) {
            // 송금 계좌 트랜잭션 기록
            transactionDAO.insertTransaction(
                TransactionDTO.builder()
                    .accountNo(fromAccountNo)
                    .amount(amount)
                    .type("TRANSFER")
                    .build()
            );
            
            // 입금 계좌 트랜잭션 기록
            transactionDAO.insertTransaction(
                TransactionDTO.builder()
                    .accountNo(toAccountNo)
                    .amount(amount)
                    .type("TRANSFER")
                    .build()
            );
            return true;
        }
        return false;
    }

}
