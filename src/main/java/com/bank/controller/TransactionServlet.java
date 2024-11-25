package com.bank.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;

@WebServlet("/bank/transactions")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TransactionService transactionService = new TransactionService();
    private final AccountService accountService = new AccountService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 세션 확인 및 인증
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect(request.getContextPath() + "/bank/login.jsp");
            return;
        }

        Integer customerId = (Integer) session.getAttribute("customerId");
        String accountNo = request.getParameter("accountNo");

        try {
            // 계좌 선택 화면 제공
            if (accountNo == null || accountNo.isEmpty()) {
                List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
                request.setAttribute("accounts", accounts);
                request.getRequestDispatcher("/bank/transactions.jsp").forward(request, response);
                return;
            }

            // 거래 내역 조회 및 HTML 응답 생성
            List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccountNo(accountNo);
            StringBuilder htmlBuilder = new StringBuilder();

            if (transactions.isEmpty()) {
                htmlBuilder.append("<tr><td colspan='5'>거래 내역이 없습니다.</td></tr>");
            } else {
                for (TransactionDTO transaction : transactions) {
                    htmlBuilder.append("<tr>")
                               .append("<td>").append(transaction.getTransactionId() != null ? transaction.getTransactionId() : "").append("</td>")
                               .append("<td>").append(transaction.getAccountNo() != null ? transaction.getAccountNo() : "").append("</td>")
                               .append("<td>").append(transaction.getAmount() != null ? transaction.getAmount() : "0").append("</td>")
                               .append("<td>").append(transaction.getType() != null ? transaction.getType() : "").append("</td>")
                               .append("<td>").append(transaction.getTransactionDate() != null ? transaction.getTransactionDate() : "").append("</td>")
                               .append("</tr>");
                }
            }

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(htmlBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<tr><td colspan='5'>거래 내역을 불러오는 중 오류가 발생했습니다.</td></tr>");
        }
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accountNo = request.getParameter("accountNo");
        String amountParam = request.getParameter("amount");
        String type = request.getParameter("type");

        try {
            // 필수 입력값 검증
            if (accountNo == null || amountParam == null || type == null || accountNo.isEmpty() || amountParam.isEmpty()) {
                throw new IllegalArgumentException("필수 항목이 누락되었습니다.");
            }

            // 금액 및 거래 유형 검증
            double amount = Double.parseDouble(amountParam);
            if (amount <= 0) {
                throw new IllegalArgumentException("거래 금액은 0보다 커야 합니다.");
            }
            if (!type.equals("DEPOSIT") && !type.equals("WITHDRAWAL") && !type.equals("TRANSFER")) {
                throw new IllegalArgumentException("유효하지 않은 거래 유형입니다.");
            }

            // 거래 DTO 생성 및 저장
            TransactionDTO transaction = TransactionDTO.builder()
                    .accountNo(accountNo)
                    .amount(amount)
                    .type(type)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/addTransaction.jsp?error=" + e.getMessage());
        }
    }
}