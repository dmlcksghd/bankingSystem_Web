package com.bank.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.bank.dao.UserAccountDAO;
import com.bank.dto.AccountDTO;
import com.bank.dto.CardDTO;
import com.bank.dto.UserAccountDTO;

public class UserAccountService {
	private final UserAccountDAO userAccountDAO = new UserAccountDAO();

	// 회원가입
	public boolean registerUser(UserAccountDTO user) {
		if (user == null || user.getUserId() == null || user.getName() == null || user.getSsn() == null
				|| user.getPassword() == null) {
			return false;
		}

		// DAO를 호출하여 회원가입 처리
		return userAccountDAO.registerUser(user);
	}

	// 특정 사용자의 계좌 조회
	public List<AccountDTO> getUserAccounts(String name, String ssn) {
		if (name == null || name.isEmpty() || ssn == null || ssn.isEmpty()) {
			throw new IllegalArgumentException("Name and SSN must not be null or empty");
		}

		return userAccountDAO.getAccountsByUser(name, ssn);
	}

	// 특정 사용자의 카드 조회
	public List<CardDTO> getUserCards(String name, String ssn) {
		if (name == null || name.isEmpty() || ssn == null || ssn.isEmpty()) {
			throw new IllegalArgumentException("Name and SSN must not be null or empty");
		}

		return userAccountDAO.getCardsByUser(name, ssn);
	}

	// 로그인 검증
	public boolean login(String userId, String password) {
		UserAccountDTO user = userAccountDAO.login(userId, password);
		return user != null;
	}

	// 로그아웃
	public void logout(HttpSession session) {
		session.invalidate();
	}
}