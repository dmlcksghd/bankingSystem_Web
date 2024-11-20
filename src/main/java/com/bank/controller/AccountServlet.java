package com.bank.controller;

import com.bank.dto.AccountDTO;
import com.bank.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountService accountService = new AccountService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdParam = request.getParameter("customerId");
        if (customerIdParam == null || customerIdParam.isEmpty()) {
            response.sendRedirect("bank/accounts.jsp");
            return;
        }

        int customerId = Integer.parseInt(customerIdParam);
        List<AccountDTO> accounts = accountService.getAllAccountsByCustomerId(customerId);
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("accounts.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdParam = request.getParameter("customerId");
        String balanceParam = request.getParameter("balance");
        String status = request.getParameter("status");

        if (customerIdParam == null || balanceParam == null || status == null ||
                customerIdParam.isEmpty() || balanceParam.isEmpty() ||
                (!status.equals("ACTIVE") && !status.equals("INACTIVE") && !status.equals("CLOSED"))) {
            // 컨텍스트 경로를 포함하여 동적 경로로 수정
            response.sendRedirect(request.getContextPath() + "/bank/addAccount.jsp");
            return;
        }

        int customerId = Integer.parseInt(customerIdParam);
        double balance = Double.parseDouble(balanceParam);

        AccountDTO account = AccountDTO.builder()
                .customerId(customerId)
                .balance(balance)
                .status(status)
                .build();

        if (accountService.addAccount(account)) {
            response.sendRedirect(request.getContextPath() + "/accounts?customerId=" + customerId);
        } else {
            response.sendRedirect(request.getContextPath() + "/bank/addAccount.jsp");
        }
    }
}