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

	// 공통 메서드: ResultSet을 AccountDTO로 매핑
	private AccountDTO makeAccount(ResultSet rs) throws SQLException {
		return AccountDTO.builder().accountNo(rs.getString("ACCOUNT_NO")).customerId(rs.getInt("CUSTOMER_ID"))
				.balance(rs.getDouble("BALANCE")).status(rs.getString("STATUS")).createdAt(rs.getString("CREATED_AT"))
				.updatedAt(rs.getString("UPDATED_AT")).build();
	}

	// 특정 고객의 모든 계좌 조회
	public List<AccountDTO> getAllAccountsByCustomerId(int customerId) {
		List<AccountDTO> accounts = new ArrayList<>();
		String sql = "SELECT * FROM ACCOUNTS WHERE CUSTOMER_ID = ?";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

			st.setInt(1, customerId);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					accounts.add(makeAccount(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	// 계좌 추가
	public boolean addAccount(AccountDTO account) {
		String sql = "INSERT INTO ACCOUNTS (CUSTOMER_ID, BALANCE, STATUS) VALUES (?, ?, ?)";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

			st.setInt(1, account.getCustomerId());
			st.setDouble(2, account.getBalance());
			st.setString(3, account.getStatus());

			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 잔액 업데이트
	public boolean updateBalance(String accountNo, double newBalance) {
		String sql = "UPDATE ACCOUNTS SET BALANCE = ?, UPDATED_AT = SYSDATE WHERE ACCOUNT_NO = ?";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

			st.setDouble(1, newBalance);
			st.setString(2, accountNo);

			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
