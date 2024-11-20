package com.bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.dto.UserAccountDTO;
import com.bank.service.UserAccountService;

@WebServlet("/auth")
public class UserAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserAccountService userAccountService = new UserAccountService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("register".equals(action)) {
			// 회원가입 로직
			String userId = request.getParameter("userId");
			String name = request.getParameter("name");
			String ssn = request.getParameter("ssn");
			String password = request.getParameter("password");

			UserAccountDTO user = UserAccountDTO.builder()
					.userId(userId)
					.name(name)
					.ssn(ssn)
					.password(password)
					.build();

			boolean isSuccess = userAccountService.registerUser(user);

			if (isSuccess) {
				request.setAttribute("message", "회원가입에 성공했습니다!");
				request.setAttribute("redirect", "main.jsp");
			} else {
				request.setAttribute("message", "회원가입에 실패했습니다.");
				request.setAttribute("redirect", "register.jsp");
			}
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		}
}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("logout".equals(action)) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				userAccountService.logout(session);
			}
			response.sendRedirect("login.jsp");
		}
	}
}
