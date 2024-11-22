package com.bank.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dto.CardDTO;
import com.bank.service.CardService;

@WebServlet("/bank/cards")
public class CardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CardService cardService = new CardService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("viewCards".equals(action)) {
            String accountNo = request.getParameter("accountNo");
            List<CardDTO> cards = cardService.getAllCardsByAccountNo(accountNo);
            request.setAttribute("cards", cards);
            request.getRequestDispatcher("/bank/cards.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

}
