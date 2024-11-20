package com.bank.service;

import java.util.List;

import com.bank.dao.AccountDAO;
import com.bank.dto.AccountDTO;

public class AccountService {
	private final AccountDAO accountDAO;

	public AccountService() {
		this.accountDAO = new AccountDAO();
	}

	// 특정 고객의 모든 계좌 조회
	public List<AccountDTO> getAllAccountsByCustomerId(int customerId) {
		if (customerId <= 0) {
			throw new IllegalArgumentException("유효하지 않은 고객 ID입니다.");
		}
		return accountDAO.getAllAccountsByCustomerId(customerId);
	}

	// 계좌 추가
	public boolean addAccount(AccountDTO account) {
		if (!isValidAccount(account)) {
			throw new IllegalArgumentException("유효하지 않은 계좌입니다.");
		}
		return accountDAO.addAccount(account);
	}

	// 계좌 검증 로직
	private boolean isValidAccount(AccountDTO account) {
		return account != null && account.getCustomerId() > 0 && account.getBalance() >= 0
				&& (account.getStatus().equals("ACTIVE") || account.getStatus().equals("INACTIVE")
						|| account.getStatus().equals("CLOSED"));
	}

	// 잔액 업데이트
	public boolean updateBalance(String accountNo, double newBalance) {
		if (accountNo == null || accountNo.isEmpty()) {
			throw new IllegalArgumentException("계좌 번호는 null이거나 비어 있을 수 없습니다.");
		}
		if (newBalance < 0) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}
		return accountDAO.updateBalance(accountNo, newBalance);
	}
}
