package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dto.TransactionDTO;

import util.DBUtil;

public class TransactionDAO {
	// 공통 메서드: ResultSet을 TransactionDTO로 매핑
	private TransactionDTO makeTransaction(ResultSet rs) throws SQLException {
		return TransactionDTO.builder().transactionId(rs.getInt("TRANSACTION_ID")).accountNo(rs.getString("ACCOUNT_NO"))
				.amount(rs.getDouble("AMOUNT")).type(rs.getString("TYPE"))
				.transactionDate(rs.getString("TRANSACTION_DATE")).build();
	}

	// 특정 계좌의 모든 거래 조회
	public List<TransactionDTO> getAllTransactionsByAccountNo(String accountNo) {
		List<TransactionDTO> transactions = new ArrayList<>();
		String sql = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_NO = ? ORDER BY TRANSACTION_DATE DESC";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

			st.setString(1, accountNo);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					transactions.add(makeTransaction(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transactions;
	}
	
	// 거래 추가
    public boolean addTransaction(TransactionDTO transaction) {
        String sql = "INSERT INTO TRANSACTIONS (ACCOUNT_NO, AMOUNT, TYPE) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, transaction.getAccountNo());
            st.setDouble(2, transaction.getAmount());
            st.setString(3, transaction.getType());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
