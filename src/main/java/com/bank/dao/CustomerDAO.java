package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dto.CustomerDTO;

public class CustomerDAO {

    // 공통 메서드: ResultSet을 CustomerDTO로 매핑
    private CustomerDTO makeCustomer(ResultSet rs) throws SQLException {
        return CustomerDTO.builder()
                .customerId(rs.getInt("CUSTOMER_ID"))
                .name(rs.getString("NAME"))
                .email(rs.getString("EMAIL"))
                .phoneNumber(rs.getString("PHONE_NUMBER"))
                .createdAt(rs.getString("CREATED_AT"))
                .updatedAt(rs.getString("UPDATED_AT"))
                .build();
    }

    // 모든 고객 조회
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER_INFO";

        try (Connection conn = util.DBUtil.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                customers.add(makeCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    // 특정 고객 조회
    public CustomerDTO getCustomerById(int customerId) {
        String sql = "SELECT * FROM CUSTOMER_INFO WHERE CUSTOMER_ID = ?";
        CustomerDTO customer = null;

        try (Connection conn = util.DBUtil.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, customerId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    customer = makeCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // 고객 추가
    public boolean addCustomer(CustomerDTO customer) {
        String sql = "INSERT INTO CUSTOMER_INFO (NAME, EMAIL, PHONE_NUMBER) VALUES (?, ?, ?)";

        try (Connection conn = util.DBUtil.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, customer.getName());
            st.setString(2, customer.getEmail());
            st.setString(3, customer.getPhoneNumber());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
