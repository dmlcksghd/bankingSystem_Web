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

@WebServlet("/bank/auth.do")
public class UserAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserAccountService userAccountService = new UserAccountService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("login".equals(action)) {
			// 로그인 처리 로직
			String userId = request.getParameter("userId");
			String password = request.getParameter("password");

			UserAccountDTO user = userAccountService.login(userId, password);

			if (user != null) {
				// 로그인 성공: 세션에 사용자 정보 저장 및 main.jsp로 이동
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("customerId", user.getCustomerId());
				System.out.println("CustomerId in session: " + session.getAttribute("customerId"));
				response.sendRedirect(request.getContextPath() + "/bank/accounts?view=main");
			} else {
				// 로그인 실패: 에러 메시지와 함께 login.jsp로 다시 이동
				request.setAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
				request.getRequestDispatcher("/bank/login.jsp").forward(request, response);
			}
		} else if ("register".equals(action)) {
			// 기존 회원가입 로직
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
				request.setAttribute("redirect", request.getContextPath() + "/bank/login.jsp");
			} else {
				request.setAttribute("message", "회원가입에 실패했습니다.");
				request.setAttribute("redirect", request.getContextPath() + "/bank/register.jsp");
			}
			request.getRequestDispatcher("/bank/result.jsp").forward(request, response);
		} else if("updateUserId".equals(action)) {
			// 아이디 변경 처리 로직
			HttpSession session = request.getSession();
			Integer customerId = (Integer) session.getAttribute("customerId");
			String newUserId = request.getParameter("newUserId");
			
			if (customerId != null && newUserId != null && !newUserId.trim().isEmpty()) {
				boolean isSuccess = userAccountService.updateUserId(customerId, newUserId);
				
				if (isSuccess) {
					request.setAttribute("message", "아이디가 성공적으로 변경되었습니다.");
				} else {
					request.setAttribute("message", "아이디 변경에 실패 했습니다.");
				}
				request.setAttribute("redirect", request.getContextPath() +"/bank/accountManagement.jsp");
				request.getRequestDispatcher("/bank/result.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/bank/accountManagement.jsp");
			}
		} else if ("updatePassword".equals(action)) {
            // 비밀번호 변경 처리 로직
            HttpSession session = request.getSession(false);
            Integer customerId = (Integer) session.getAttribute("customerId");
            String newPassword = request.getParameter("newPassword");

            if (customerId != null && newPassword != null && !newPassword.trim().isEmpty()) {
                boolean isSuccess = userAccountService.updatePassword(customerId, newPassword);

                if (isSuccess) {
                    request.setAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
                } else {
                    request.setAttribute("message", "비밀번호 변경에 실패했습니다.");
                }
                request.setAttribute("redirect", request.getContextPath() +"/bank/accountManagement.jsp");
                request.getRequestDispatcher("/bank/result.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/bank/accountManagement.jsp");
            }
        }
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 로그인 페이지로 이동
		request.getRequestDispatcher("login.jsp").forward(request, response);
		
		String action = request.getParameter("action");

		if ("logout".equals(action)) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate(); // 세션 무효화
			}
			response.sendRedirect(request.getContextPath() + "bank/login.jsp"); // 로그인 페이지로 이동
		}
	}
}