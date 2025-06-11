package io.github.chamikathereal.auction.web.servlet;

import io.github.chamikathereal.auction.ejb.remote.UserSessionManager;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @EJB
    private UserSessionManager userSessionManager;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            userSessionManager.invalidateSession(session.getId());
            System.out.println("userSessionManager: " + userSessionManager + " invalidated" + session.getId());
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}

