package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dto.AccountDTO;
import com.bank.dto.CardDTO;
import com.bank.dto.UserAccountDTO;

import util.DBUtil;

public class UserAccountDAO {
    // 회원가입
	public boolean registerUser(UserAccountDTO user) {
	    String insertCustomerSQL = "INSERT INTO CUSTOMER_INFO (NAME, SSN) VALUES (?, ?)";
	    String selectCustomerIdSQL = "SELECT CUSTOMER_ID FROM CUSTOMER_INFO WHERE SSN = ?";
	    String insertUserAccountSQL = "INSERT INTO USER_ACCOUNT (USER_ID, CUSTOMER_ID, PASSWORD) VALUES (?, ?, ?)";

	    Connection conn = null;
	    try {
	        conn = DBUtil.getConnection();
	        conn.setAutoCommit(false);

	        // CUSTOMER_INFO에 데이터 삽입
	        try (PreparedStatement st = conn.prepareStatement(insertCustomerSQL)) {
	            st.setString(1, user.getName());
	            st.setString(2, user.getSsn());
	            st.executeUpdate();
	        }

	        // CUSTOMER_ID 조회
	        int customerId = 0;
	        try (PreparedStatement st = conn.prepareStatement(selectCustomerIdSQL)) {
	            st.setString(1, user.getSsn());
	            try (ResultSet rs = st.executeQuery()) {
	                if (rs.next()) {
	                    customerId = rs.getInt("CUSTOMER_ID");
	                }
	            }
	        }

	        // USER_ACCOUNT에 데이터 삽입
	        try (PreparedStatement st = conn.prepareStatement(insertUserAccountSQL)) {
	            st.setString(1, user.getUserId());
	            st.setInt(2, customerId);
	            st.setString(3, user.getPassword());
	            st.executeUpdate();
	        }

	        conn.commit();
	        return true;
	    } catch (SQLException e) {
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException closeEx) {
	                closeEx.printStackTrace();
	            }
	        }
	    }
	}




    // 해당 사용자의 계좌 조회
	public List<AccountDTO> getAccountsByUser(String name, String ssn) {
	    String sql = "SELECT a.ACCOUNT_NO, a.BALANCE, a.STATUS, a.CREATED_AT " +
	                 "FROM ACCOUNT a " +
	                 "JOIN CUSTOMER_INFO c ON a.CUSTOMER_ID = c.CUSTOMER_ID " +
	                 "WHERE c.NAME = ? AND c.SSN = ?";
	    List<AccountDTO> accounts = new ArrayList<>();

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement st = conn.prepareStatement(sql)) {

	        st.setString(1, name);
	        st.setString(2, ssn);

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                accounts.add(AccountDTO.builder()
	                        .accountNo(rs.getString("ACCOUNT_NO"))
	                        .balance(rs.getDouble("BALANCE"))
	                        .status(rs.getString("STATUS"))
	                        .createdAt(rs.getString("CREATED_AT"))
	                        .build());
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching accounts for user: " + name);
	        e.printStackTrace();
	    }
	    return accounts;
	}


    // 해당 사용자의 카드 조회
	public List<CardDTO> getCardsByUser(String name, String ssn) {
	    String sql = "SELECT c.CARD_NO, c.LIMIT_AMOUNT, c.ISSUED_AT " +
	                 "FROM CARD c " +
	                 "JOIN ACCOUNT a ON c.ACCOUNT_NO = a.ACCOUNT_NO " +
	                 "JOIN CUSTOMER_INFO ci ON a.CUSTOMER_ID = ci.CUSTOMER_ID " +
	                 "WHERE ci.NAME = ? AND ci.SSN = ?";
	    List<CardDTO> cards = new ArrayList<>();

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement st = conn.prepareStatement(sql)) {

	        st.setString(1, name);
	        st.setString(2, ssn);

	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                cards.add(CardDTO.builder()
	                        .cardNo(rs.getInt("CARD_NO")) // 정확한 타입 확인 필요
	                        .limitAmount(rs.getDouble("LIMIT_AMOUNT"))
	                        .issuedAt(rs.getString("ISSUED_AT"))
	                        .build());
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching cards for user: " + name);
	        e.printStackTrace();
	    }
	    return cards;
	}


    // 로그인 검증
	public UserAccountDTO login(String userId, String password) {
	    String sql = "SELECT ua.USER_ID, ua.PASSWORD, ua.CREATED_AT, ua.UPDATED_AT, " +
	                 "ci.NAME, ci.SSN " +
	                 "FROM USER_ACCOUNT ua " +
	                 "JOIN CUSTOMER_INFO ci ON ua.CUSTOMER_ID = ci.CUSTOMER_ID " +
	                 "WHERE ua.USER_ID = ? AND ua.PASSWORD = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement st = conn.prepareStatement(sql)) {

	        st.setString(1, userId);
	        st.setString(2, password);

	        try (ResultSet rs = st.executeQuery()) {
	            if (rs.next()) {
	                return UserAccountDTO.builder()
	                        .userId(rs.getString("USER_ID"))
	                        .password(rs.getString("PASSWORD"))
	                        .name(rs.getString("NAME"))
	                        .ssn(rs.getString("SSN"))
	                        .createdAt(rs.getString("CREATED_AT"))
	                        .updatedAt(rs.getString("UPDATED_AT"))
	                        .build();
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error during login for user: " + userId);
	        e.printStackTrace();
	    }
	    return null;
	}

}
