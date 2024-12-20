package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dto.AccountDTO;

import util.DBUtil;

public class AccountDAO {

    // 특정 고객의 모든 계좌 조회
	public List<AccountDTO> getAccountsByCustomerId(int customerId) {
	    List<AccountDTO> accounts = new ArrayList<>();
	    String sql = "SELECT ACCOUNT_NO, CUSTOMER_ID, BALANCE, STATUS, CREATED_AT, UPDATED_AT " +
	                 "FROM ACCOUNTS WHERE CUSTOMER_ID = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement st = conn.prepareStatement(sql)) {

	        st.setInt(1, customerId);
	        try (ResultSet rs = st.executeQuery()) {
	            while (rs.next()) {
	                AccountDTO account = makeAccount(rs);
	                accounts.add(account);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving accounts for customerId: " + customerId);
	        e.printStackTrace();
	    }
	    System.out.println("Accounts from DAO: " + accounts); // 로그 추가
	    return accounts;
	}


    // 계좌 데이터 매핑
    private AccountDTO makeAccount(ResultSet rs) throws SQLException {
        return AccountDTO.builder()
                .accountNo(rs.getString("ACCOUNT_NO"))
                .customerId(rs.getInt("CUSTOMER_ID"))
                .balance(rs.getDouble("BALANCE"))
                .status(rs.getString("STATUS"))
                .createdAt(rs.getString("CREATED_AT"))
                .updatedAt(rs.getString("UPDATED_AT"))
                .build();
    }
    
    // 잔액 조회
    public double getBalance(String accountNo) throws SQLException {
        String sql = "SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_NO = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("BALANCE");
                } else {
                    throw new SQLException("해당 계좌를 찾을 수 없습니다: " + accountNo);
                }
            }
        }
    }
    
    
    
    // 잔액 업데이트 (초기화, 절대값 변경)
    public boolean updateBalance(String accountNo, double newBalance, Connection connection) {
        String sql = "UPDATE ACCOUNTS SET BALANCE = BALANCE + ?, UPDATED_AT = SYSDATE WHERE ACCOUNT_NO = ?";
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setDouble(1, newBalance);
            st.setString(2, accountNo);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateBalanceIncrement(String accountNo, double amount) {
        String sql = "UPDATE ACCOUNTS SET BALANCE = BALANCE + ?, UPDATED_AT = SYSDATE WHERE ACCOUNT_NO = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setString(2, accountNo);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    	
    }

    // 계좌 상태 업데이트
    public boolean updateAccountStatus(String accountNo, String status) {
        String sql = "UPDATE ACCOUNTS SET STATUS = ?, UPDATED_AT = SYSDATE WHERE ACCOUNT_NO = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, status);
            st.setString(2, accountNo);

            return st.executeUpdate() > 0; // 성공적으로 업데이트된 경우 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
