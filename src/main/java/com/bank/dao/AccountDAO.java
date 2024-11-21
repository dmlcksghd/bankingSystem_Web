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
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, customerId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                AccountDTO account = makeAccount(rs);
	                accounts.add(account);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error retrieving accounts for customerId: " + customerId);
	        e.printStackTrace();
	    }
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

    // 잔액 업데이트
    public boolean updateBalance(String accountNo, double newBalance) {
        String sql = "UPDATE ACCOUNTS SET BALANCE = ?, UPDATED_AT = SYSDATE WHERE ACCOUNT_NO = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNo);

            return pstmt.executeUpdate() > 0; // 성공적으로 업데이트된 경우 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 계좌 상태 업데이트
    public boolean updateAccountStatus(String accountNo, String status) {
        String sql = "UPDATE ACCOUNTS SET STATUS = ?, UPDATED_AT = SYSDATE WHERE ACCOUNT_NO = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setString(2, accountNo);

            return pstmt.executeUpdate() > 0; // 성공적으로 업데이트된 경우 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
