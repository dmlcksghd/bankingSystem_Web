package com.bank.controller;

import com.bank.dto.CardDTO;
import com.bank.service.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cards")
public class CardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CardService cardService = new CardService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        if (accountNo == null || accountNo.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<CardDTO> cards = cardService.getAllCardsByAccountNo(accountNo);
        request.setAttribute("cards", cards);
        request.getRequestDispatcher("/cards.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        String limitAmountParam = request.getParameter("limitAmount");

        if (accountNo == null || limitAmountParam == null || accountNo.isEmpty() || limitAmountParam.isEmpty()) {
            response.sendRedirect("addCard.jsp");
            return;
        }

        double limitAmount = Double.parseDouble(limitAmountParam);

        CardDTO card = CardDTO.builder()
                .accountNo(accountNo)
                .limitAmount(limitAmount)
                .build();

        if (cardService.addCard(card)) {
            response.sendRedirect("cards?accountNo=" + accountNo);
        } else {
            response.sendRedirect("addCard.jsp");
        }
    }
}
