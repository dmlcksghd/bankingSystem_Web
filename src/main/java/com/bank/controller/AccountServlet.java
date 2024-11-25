package com.bank.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;

@WebServlet("/bank/accounts")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();

    // 계좌 조회 처리 (GET 요청)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/bank/login.jsp");
            return;
        }

        // 세션에서 고객 ID 가져오기
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            response.sendRedirect(request.getContextPath() + "/bank/login.jsp");
            return;
        }

        try {
            // 기존 계좌 관리 페이지 로직
            if ("accounts".equals(request.getParameter("view"))) {
                List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
                request.setAttribute("accounts", accounts);
                request.getRequestDispatcher("/bank/accounts.jsp").forward(request, response);
                return;
            }

            // main.jsp에 필요한 데이터 전달
            if ("main".equals(request.getParameter("view"))) {
                // 계좌 요약 정보 가져오기
                Map<String, Object> summary = accountService.getAccountSummary(customerId);
                request.setAttribute("totalAccounts", summary.get("totalAccounts"));
                request.setAttribute("totalBalance", summary.get("totalBalance"));

                // 고객의 첫 번째 계좌로 최근 거래 내역 가져오기
                List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
                request.setAttribute("accounts", accounts);
                
                if (!accounts.isEmpty()) {
                    String accountNo = accounts.get(0).getAccountNo();
                    List<TransactionDTO> recentTransactions = transactionService.getRecentTransactions(accountNo, 5);
                    request.setAttribute("recentTransactions", recentTransactions);
                }

                // main.jsp로 데이터 전달
                request.getRequestDispatcher("/bank/main.jsp").forward(request, response);
                return;
            }
            
         // "transactions" 요청 처리
            if ("transactions".equals(request.getParameter("view"))) {
                List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
                request.setAttribute("accounts", accounts);
                System.out.println("Accounts set as attribute: " + accounts); // 확인 로그 추가
                
                // 기본 계좌 또는 선택된 계좌의 거래 내역 처리
                String accountNo = request.getParameter("accountNo");
                if (accountNo == null && !accounts.isEmpty()) {
                    accountNo = accounts.get(0).getAccountNo(); // 기본 계좌 설정
                    System.out.println("Default accountNo set to: " + accountNo);
                }

                if (accountNo != null) {
                    response.sendRedirect(request.getContextPath() + "/bank/transactions?accountNo");
                    return;
                }

                System.out.println("No account available for transactions view.");
                response.sendRedirect(request.getContextPath() + "/bank/transactions");
                return;
            } 
            
         // 기본 계좌 관리 페이지 처리
            List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
            request.setAttribute("accounts", accounts);
            request.getRequestDispatcher("/bank/accounts.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
        }
    }

    // 상태 변경 및 송금 처리
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateStatus".equals(action)) {
            handleUpdateStatus(request, response);
        } else if ("transfer".equals(action)) {
            handleTransfer(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
        }
    }

    private void handleUpdateStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String accountNo = request.getParameter("accountNo");
        String status = request.getParameter("status");
        
        // 디버깅: 전달된 값 확인
        System.out.println("Received accountNo: " + accountNo);
        System.out.println("Received status: " + status);

        if (status == null || status.isEmpty()) {
            status = "INACTIVE"; // 기본값 설정
        }
         
        if ("ACTIVE".equalsIgnoreCase(status) || "INACTIVE".equalsIgnoreCase(status)) {
            boolean success = accountService.updateAccountStatus(accountNo, status);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/bank/accounts?view=accounts");
            } else {
                response.getWriter().write("계좌 상태 변경에 실패했습니다.");
            }
        } else {
        	System.out.println("Invalid status value: " + status);
            response.getWriter().write("잘못된 상태 값입니다.");
        }
    }

    private void handleTransfer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String fromAccountNo = request.getParameter("fromAccountNo");
        String toAccountNo = request.getParameter("toAccountNo");
        double amount;

        try {
            amount = Double.parseDouble(request.getParameter("amount"));
            boolean success = accountService.transferAmount(fromAccountNo, toAccountNo, amount);

            if (success) {
                request.getSession().setAttribute("transferMessage", "송금이 성공적으로 완료되었습니다.");
                response.sendRedirect(request.getContextPath() + "/bank/accounts?view=accounts");
            } else {
                request.getSession().setAttribute("transferMessage", "송금에 실패했습니다.");
                response.sendRedirect(request.getContextPath() + "/bank/accounts?view=accounts");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("transferMessage", "잘못된 금액 입력입니다.");
            response.sendRedirect(request.getContextPath() + "/bank/accounts?view=accounts");
        } catch (Exception e) {
            request.getSession().setAttribute("transferMessage", "송금 중 오류가 발생했습니다.");
            response.sendRedirect(request.getContextPath() + "/bank/accounts?view=accounts");
            e.printStackTrace();
        }
    }
}
