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

        if (customerId == null) {
            response.sendRedirect(request.getContextPath() + "/bank/login.jsp");
            return;
        }

        // AccountService를 통해 데이터 가져오기
        List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
        // JSP로 데이터 전달
        request.setAttribute("accounts", accounts);

        request.getRequestDispatcher("/bank/accounts.jsp").forward(request, response);
    }

}