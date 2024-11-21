package com.bank.service;

import java.util.List;

import com.bank.dao.AccountDAO;
import com.bank.dto.AccountDTO;

public class AccountService {
    private final AccountDAO accountDAO;

    // 생성자
    public AccountService() {
        this.accountDAO = new AccountDAO();
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
        if (accountNo == null || accountNo.isEmpty() || status == null || status.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 계좌 번호 또는 상태입니다.");
        }
        if (!(status.equalsIgnoreCase("ACTIVE") || status.equalsIgnoreCase("INACTIVE"))) {
            throw new IllegalArgumentException("변경할 수 없는 계좌 상태입니다.");
        }
        return accountDAO.updateAccountStatus(accountNo, status);
    }
}
