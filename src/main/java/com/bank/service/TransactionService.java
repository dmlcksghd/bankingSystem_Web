package com.bank.service;

import java.util.List;

import com.bank.dao.TransactionDAO;
import com.bank.dto.TransactionDTO;

public class TransactionService {
	private final TransactionDAO transactionDAO;

	public TransactionService() {
		this.transactionDAO = new TransactionDAO();
	}

	// 특정 계좌의 모든 거래 조회
	public List<TransactionDTO> getAllTransactionsByAccountNo(String accountNo) {
		if (accountNo == null || accountNo.isEmpty()) {
			throw new IllegalArgumentException("계좌 번호는 null이거나 비어 있을 수 없습니다.");
		}
		return transactionDAO.getAllTransactionsByAccountNo(accountNo);
	}

	// 거래 추가
	public boolean addTransaction(TransactionDTO transaction) {
		if (!isValidTransaction(transaction)) {
			throw new IllegalArgumentException("유효하지 않은 거래 정보입니다.");
		}
		return transactionDAO.addTransaction(transaction);
	}

	// 거래 검증 로직
	private boolean isValidTransaction(TransactionDTO transaction) {
		return transaction != null && transaction.getAccountNo() != null && !transaction.getAccountNo().isEmpty()
				&& transaction.getAmount() > 0 && (transaction.getType().equals("DEPOSIT")
						|| transaction.getType().equals("WITHDRAWAL") || transaction.getType().equals("TRANSFER"));
	}
}