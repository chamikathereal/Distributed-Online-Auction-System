package io.github.chamikathereal.auction.web.servlet;

import io.github.chamikathereal.auction.core.model.User;
import io.github.chamikathereal.auction.ejb.remote.UserSessionManager;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserSessionManager userSessionManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name != null && email != null) {
            User user = new User(name, email);
            HttpSession session = request.getSession();
            // save user to session
            session.setAttribute("user", user);

            System.out.println("userSessionManager: " + userSessionManager + " " + " invalidated: " + session.getId() + "user: " + user);

            // save user to UserSessionManager EJB as well
            userSessionManager.createSession(session.getId(), user);
        }


        response.sendRedirect("home");
    }
}
