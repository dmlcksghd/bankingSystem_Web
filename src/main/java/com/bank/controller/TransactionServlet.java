package com.bank.controller;

import com.bank.dto.TransactionDTO;
import com.bank.service.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TransactionService transactionService = new TransactionService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        if (accountNo == null || accountNo.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccountNo(accountNo);
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/transactions.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String amountParam = request.getParameter("amount");
        String type = request.getParameter("type");

        if (accountNo == null || amountParam == null || type == null ||
                accountNo.isEmpty() || amountParam.isEmpty() ||
                (!type.equals("DEPOSIT") && !type.equals("WITHDRAWAL") && !type.equals("TRANSFER"))) {
            response.sendRedirect("addTransaction.jsp");
            return;
        }

        double amount = Double.parseDouble(amountParam);

        TransactionDTO transaction = TransactionDTO.builder()
                .accountNo(accountNo)
                .amount(amount)
                .type(type)
                .build();

        if (transactionService.addTransaction(transaction)) {
            response.sendRedirect("transactions?accountNo=" + accountNo);
        } else {
            response.sendRedirect("addTransaction.jsp");
        }
    }
}
