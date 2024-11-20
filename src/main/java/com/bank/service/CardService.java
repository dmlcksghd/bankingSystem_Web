package com.bank.service;

import java.util.List;

import com.bank.dao.CardDAO;
import com.bank.dto.CardDTO;

public class CardService {
	private final CardDAO cardDAO;

	public CardService() {
		this.cardDAO = new CardDAO();
	}

	// 특정 계좌의 모든 카드 조회
	public List<CardDTO> getAllCardsByAccountNo(String accountNo) {
		if (accountNo == null || accountNo.isEmpty()) {
			throw new IllegalArgumentException("계좌 번호는 null이거나 비어 있을 수 없습니다.");
		}
		return cardDAO.getAllCardsByAccountNo(accountNo);
	}

	// 카드 추가
	public boolean addCard(CardDTO card) {
		if (!isValidCard(card)) {
			throw new IllegalArgumentException("유효하지 않은 카드 정보입니다.");
		}
		return cardDAO.addCard(card);
	}

	// 카드 한도 업데이트
	public boolean updateLimit(String cardNo, double newLimit) {
		if (cardNo == null || cardNo.isEmpty()) {
			throw new IllegalArgumentException("카드번호가 null 또는 empty입니다.");
		}
		if (newLimit <= 0) {
			throw new IllegalArgumentException("한도 금액은 0보다 커야 합니다.");
		}
		return cardDAO.updateLimit(cardNo, newLimit);
	}

	// 카드 검증 로직
	private boolean isValidCard(CardDTO card) {
		return card != null && card.getAccountNo() != null && !card.getAccountNo().isEmpty()
				&& card.getLimitAmount() > 0;
	}
}
