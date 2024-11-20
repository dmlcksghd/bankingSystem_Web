package com.bank.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.CustomerDAO;
import com.bank.dto.CustomerDTO;
import com.bank.service.CustomerService;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CustomerService customerService = new CustomerService(); // 필드로 선언
    private CustomerDAO customerDAO = new CustomerDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/customers.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 클라이언트 요청 값 가져오기
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        // 입력 값 검증
        if (name == null || name.isEmpty() || email == null || !isValidEmail(email) || phoneNumber == null || phoneNumber.length() != 13) {
            // 잘못된 입력 시 다시 입력 폼으로 리디렉트
            response.sendRedirect("addCustomer.jsp");
            return;
        }

        // DTO 생성
        CustomerDTO customer = CustomerDTO.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        // 고객 추가
        if (customerService.addCustomer(customer)) {
            response.sendRedirect("customers"); // 성공 시 고객 목록 페이지로 리디렉트
        } else {
            response.sendRedirect("addCustomer.jsp");
        }
    }

    // 이메일 검증 메서드
    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$");
    }

}