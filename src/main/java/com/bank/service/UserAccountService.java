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

    // 로그인 검증 및 사용자 정보 반환
    public UserAccountDTO login(String userId, String password) {
        return userAccountDAO.login(userId, password);
    }

    // 로그아웃
    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }
    
    // 로그인 아이디 변경
    public boolean updateUserId(int customerId, String newUserId) {
        if (customerId <= 0 || newUserId == null || newUserId.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 입력 값입니다.");
        }
        return userAccountDAO.updateUserId(customerId, newUserId);
    }
    
 // 로그인 비밀번호 변경
    public boolean updatePassword(int customerId, String newPassword) {
        if (customerId <= 0 || newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 입력 값입니다.");
        }
        return userAccountDAO.updatePassword(customerId, newPassword);
    }
    
}
