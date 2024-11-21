package com.bank.controller;

import com.bank.dto.AccountDTO;
import com.bank.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bank/accounts")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountService accountService = new AccountService();

    // 계좌 조회 처리 (GET 요청)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/bank/login.jsp");
            return;
        }

        Integer customerId = (Integer) session.getAttribute("customerId");
        System.out.println("Customer ID from session: " + customerId); // 세션에서 고객 ID 확인

        if (customerId == null) {
            response.sendRedirect(request.getContextPath() + "/bank/login.jsp");
            return;
        }

        // AccountService를 통해 데이터 가져오기
        List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No accounts found for customerId: " + customerId); // 데이터 없음 로그
        } else {
            System.out.println("Accounts retrieved: " + accounts); // 정상적으로 가져온 데이터 로그
        }

        // JSP로 데이터 전달
        request.setAttribute("accounts", accounts);
        System.out.println("Accounts set as request attribute: " + accounts); // 전달된 데이터 확인 로그

        request.getRequestDispatcher("/bank/accounts.jsp").forward(request, response);
    }

}