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

    public List<TransactionDTO> getTransactionsByAccountNo(String accountNo) {
        List<TransactionDTO> transactions = new ArrayList<>();
        String sql = "SELECT TRANSACTION_ID, ACCOUNT_NO, AMOUNT, TYPE, TRANSACTION_DATE FROM TRANSACTIONS WHERE ACCOUNT_NO = ? ORDER BY TRANSACTION_DATE DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(makeTransaction(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    public boolean insertTransaction(TransactionDTO transaction, Connection connection) {
        String sql = "INSERT INTO TRANSACTIONS (ACCOUNT_NO, AMOUNT, TYPE, TRANSACTION_DATE) VALUES (?, ?, ?, SYSDATE)";

        	try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // 금액을 양수로 변환하여 삽입
            pstmt.setString(1, transaction.getAccountNo());
            pstmt.setDouble(2, Math.abs(transaction.getAmount())); // 항상 양수로 처리
            pstmt.setString(3, transaction.getType());
            
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: " + transaction.getAccountNo() + ", " + Math.abs(transaction.getAmount()) + ", " + transaction.getType());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private TransactionDTO makeTransaction(ResultSet rs) throws SQLException {
        return TransactionDTO.builder()
                .transactionId(rs.getInt("TRANSACTION_ID"))
                .accountNo(rs.getString("ACCOUNT_NO"))
                .amount(rs.getDouble("AMOUNT"))
                .type(rs.getString("TYPE"))
                .transactionDate(rs.getDate("TRANSACTION_DATE").toString())
                .build();
    }
}