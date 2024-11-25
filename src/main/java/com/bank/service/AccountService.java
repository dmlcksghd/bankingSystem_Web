package com.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;

import util.DBUtil;

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
        System.out.println("Accounts fetched from DB: " + accounts); // 로그 추가
        return accounts;
    }

    public Map<String, Object> getAccountSummary(int customerId) {
    	List<AccountDTO> accounts = accountDAO.getAccountsByCustomerId(customerId);
    	
    	int totalAccounts = accounts.size();
    	double totalBalance = accounts.stream().mapToDouble(AccountDTO::getBalance).sum();
    	
    	Map<String, Object> summary = new HashMap<>();
    	summary.put("totalAccounts", totalAccounts);
    	summary.put("totalBalance", totalBalance);
    	
    	return summary;
    }


    // 계좌 잔액 업데이트
    public boolean updateBalance(String accountNo, double newBalance) {
        if (accountNo == null || accountNo.isEmpty()) {
            throw new IllegalArgumentException("계좌 번호는 null이거나 비어 있을 수 없습니다.");
        }
        if (newBalance < 0) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        return accountDAO.updateBalance(accountNo, newBalance, null);
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
    public boolean transferAmount(String fromAccountNo, String toAccountNo, double amount) throws SQLException {
        if (fromAccountNo == null || fromAccountNo.isEmpty() || toAccountNo == null || toAccountNo.isEmpty()) {
            throw new IllegalArgumentException("계좌 번호가 유효하지 않습니다.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("송금 금액은 0보다 커야 합니다.");
        }

        Connection connection = null;

        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false); // 트랜잭션 시작

            // 송금 계좌 잔액 확인
            double fromBalance = accountDAO.getBalance(fromAccountNo);

            if (fromBalance < amount) {
                throw new IllegalArgumentException("잔액이 부족합니다.");
            }

            // 송금 계좌에서 금액 차감
            if (!accountDAO.updateBalance(fromAccountNo, -amount, connection)) {
                throw new SQLException("송금 계좌 잔액 업데이트 실패");
            }

            // 입금 계좌에 금액 추가
            if (!accountDAO.updateBalance(toAccountNo, amount, connection)) {
                throw new SQLException("입금 계좌 잔액 업데이트 실패");
            }

            // 송금 트랜잭션 기록
            transactionDAO.insertTransaction(
                TransactionDTO.builder()
                    .accountNo(fromAccountNo)
                    .amount(-amount)
                    .type("TRANSFER")
                    .build(),
                connection
            );

            // 입금 트랜잭션 기록
            transactionDAO.insertTransaction(
                TransactionDTO.builder()
                    .accountNo(toAccountNo)
                    .amount(amount)
                    .type("TRANSFER")
                    .build(),
                connection
            );

            connection.commit(); // 트랜잭션 커밋
            return true;

        } catch (Exception e) {
            if (connection != null) {
                connection.rollback(); // 트랜잭션 롤백
            }
            throw e;

        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // 자동 커밋 복구
                connection.close();
            }
        }
    }

}