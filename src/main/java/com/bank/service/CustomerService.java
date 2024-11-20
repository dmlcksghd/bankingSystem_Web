package com.bank.service;

import java.util.List;

import com.bank.dao.CustomerDAO;
import com.bank.dto.CustomerDTO;

public class CustomerService {
	private final CustomerDAO customerDAO;
	
	public CustomerService() {
		this.customerDAO = new CustomerDAO();
	}
	
	//모든 고객 조회
	public List<CustomerDTO> getAllCustomers() {
		return customerDAO.getAllCustomers();
	}
	
	//고객추가(이메일 형식 검증 포함)
	public boolean addCustomer(CustomerDTO customer) {
		if(!isValidEmail(customer.getEmail())) {
			throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
		}
		return customerDAO.addCustomer(customer);
	}

	private boolean isValidEmail(String email) {
		return email != null && email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z] {2,}$");
	}
	
	// 특정 고객 조회
    public CustomerDTO getCustomerById(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }
}
