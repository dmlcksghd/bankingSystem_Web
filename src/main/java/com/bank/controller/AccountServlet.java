package com.bank.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dto.AccountDTO;
import com.bank.service.AccountService;

@WebServlet("/bank/accounts")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountService accountService = new AccountService();

    // 계좌 조회 처리 (GET 요청)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
        	response.getWriter().write("<option value=''>로그인이 필요합니다.</option>");
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
    
    // 상태 변경 처리
    private void handleUpdateStatus(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException {
    	String accountNo = request.getParameter("accountNo");
    	String status = request.getParameter("status");
    	
    	// 로그 출력
        System.out.println("Received accountNo: " + accountNo);
        System.out.println("Received status: " + status);
    	
    	String newStatus = "INACTIVE";
    	if("ACTIVE".equals(status)) {
    		newStatus = "ACTIVE";
    	}
    	
    	System.out.println("계좌 상태가 변경되었습니다 : " + accountNo + " / " + newStatus);
    	
    	boolean success = accountService.updateAccountStatus(accountNo, newStatus);
    	if (success) {
            response.sendRedirect(request.getContextPath() + "/bank/accounts");
        } else {
            response.getWriter().write("상태 업데이트에 실패했습니다.");
        }
    }
    
    // 송금 처리
    private void handleTransfer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	String fromAccountNo = request.getParameter("fromAccountNo");
    	String toAccountNo = request.getParameter("toAccountNo");
    	double amount;
    	
    	try {
    		 // 금액 파싱
            amount = Double.parseDouble(request.getParameter("amount"));

            // 송금 처리
            boolean success = accountService.transferAmount(fromAccountNo, toAccountNo, amount);

            // 결과 처리
            if (success) {
            	request.getSession().setAttribute("transferMessage", "송금이 성공적으로 완료되었습니다.");
            	response.sendRedirect(request.getContextPath() + "/bank/accounts");
                System.out.println("금액: " + amount + "원 / 송금계좌: " + fromAccountNo + " 입급계좌: " + toAccountNo);
            } else {
            	request.getSession().setAttribute("transferMessage", "송금에 실패했습니다.");
                response.sendRedirect(request.getContextPath() + "/bank/accounts");
            }
            
            System.out.println("금액: " + amount + "원 / 송금계좌: " + fromAccountNo + " 입급계좌: " + toAccountNo);
            
    	} catch (NumberFormatException e) {
    		request.getSession().setAttribute("transferMessage", "잘못된 금액입니다.");
    		response.sendRedirect(request.getContextPath() + "/bank/accounts");
    		e.printStackTrace();
    		return;
    	} catch (SQLException e) {
    		request.getSession().setAttribute("transferMessage", "송금 중 오류가 발생했습니다: " + e.getMessage());
	        response.sendRedirect(request.getContextPath() + "/bank/accounts");
	        e.printStackTrace();
		} catch (IllegalArgumentException e) {
			request.getSession().setAttribute("transferMessage", "잘못된 금액입니다. " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/bank/accounts");
		} catch (Exception e) {
			request.getSession().setAttribute("transferMessage", "송금 중 오류가 발생했습니다." + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/bank/accounts");
	        e.printStackTrace();
		}
    	
    }

}