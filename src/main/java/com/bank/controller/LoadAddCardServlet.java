package com.bank.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bank/loadAddCardForm")
public class LoadAddCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String accountNo = request.getParameter("accountNo");
		
		request.setAttribute("accountNo", accountNo);
		request.getRequestDispatcher("/WEB-INF/views/addCardForm.jsp").forward(request, response);
	}

}
