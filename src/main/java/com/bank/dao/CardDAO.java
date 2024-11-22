package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dto.CardDTO;

import util.DBUtil;

public class CardDAO {
	// 공통 메서드: ResultSet을 CardDTO로 매핑
	private CardDTO makeCard(ResultSet rs) throws SQLException {
		return CardDTO.builder().cardNo(rs.getInt("CARD_NO")).accountNo(rs.getString("ACCOUNT_NO"))
				.limitAmount(rs.getDouble("LIMIT_AMOUNT")).issuedAt(rs.getString("ISSUED_AT")).build();
	}

	// 특정 계좌의 모든 카드 조회
	public List<CardDTO> getAllCardsByAccountNo(String accountNo) {
		List<CardDTO> cards = new ArrayList<>();
		String sql = "SELECT * FROM CARDS WHERE ACCOUNT_NO = ?";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

			st.setString(1, accountNo);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					cards.add(makeCard(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cards;
	}

	// 카드 추가
	public boolean addCard(CardDTO card) {
		String sql = "INSERT INTO CARDS (ACCOUNT_NO, LIMIT_AMOUNT, ISSUED_AT) VALUES (?, ?, SYSDATE)";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

			st.setString(1, card.getAccountNo());
			st.setDouble(2, card.getLimitAmount());

			return st.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 카드 한도 업데이트
	public boolean updateLimit(String cardNo, double newLimit) {
		String sql = "UPDATE CARDS SET LIMIT_AMOUNT = ?, ISSUED_AT = SYSDATE WHERE CARD_NO = ?";

		try (Connection conn = DBUtil.getConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

			st.setDouble(1, newLimit);
			st.setString(2, cardNo);

			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
