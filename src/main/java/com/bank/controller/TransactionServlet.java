package com.bank.controller;

import com.bank.dto.TransactionDTO;
import com.bank.service.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bank/transactions")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TransactionService transactionService = new TransactionService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accountNo = request.getParameter("accountNo");

        // 계좌 번호 유효성 검사
        if (accountNo == null || accountNo.isEmpty()) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<tr><td colspan='5'>계좌를 먼저 선택하세요.</td></tr>");
            return;
        }

        try {
            // 거래 내역 조회
            List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccountNo(accountNo);

            // Ajax 요청에 대한 HTML 응답 생성
            StringBuilder transactionHtml = new StringBuilder();
            for (TransactionDTO transaction : transactions) {
                transactionHtml.append("<tr>")
                               .append("<td>").append(transaction.getTransactionId()).append("</td>")
                               .append("<td>").append(transaction.getAccountNo()).append("</td>")
                               .append("<td>").append(transaction.getAmount()).append("</td>")
                               .append("<td>").append(transaction.getType()).append("</td>")
                               .append("<td>").append(transaction.getTransactionDate()).append("</td>")
                               .append("</tr>");
            }

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(transactionHtml.toString());
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
