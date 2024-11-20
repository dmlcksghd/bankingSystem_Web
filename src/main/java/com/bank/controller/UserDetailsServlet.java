package com.bank.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dto.AccountDTO;
import com.bank.dto.CardDTO;
import com.bank.service.UserAccountService;

/**
 * Servlet implementation class UserDetailsServlet
 */
@WebServlet("/UserDetailsServlet")
public class UserDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserAccountService userAccountService = new UserAccountService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 사용자의 이름과 주민등록번호 가져오기
        String name = request.getParameter("name");
        String ssn = request.getParameter("ssn");

        if (name == null || name.isEmpty() || ssn == null || ssn.isEmpty()) {
            response.sendRedirect("error.jsp"); // 입력값이 없을 경우 에러 페이지로 리디렉트
            return;
        }

        // 계좌 및 카드 정보 가져오기
        List<AccountDTO> accounts = userAccountService.getUserAccounts(name, ssn);
        List<CardDTO> cards = userAccountService.getUserCards(name, ssn);

        // 데이터를 JSP로 전달
        request.setAttribute("accounts", accounts);
        request.setAttribute("cards", cards);

        // 사용자 세부 정보 JSP로 포워드
        request.getRequestDispatcher("/userDetails.jsp").forward(request, response);
    }
	
}
