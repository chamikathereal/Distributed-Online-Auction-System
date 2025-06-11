package io.github.chamikathereal.auction.web.servlet;

import io.github.chamikathereal.auction.core.model.Bid;
import io.github.chamikathereal.auction.core.model.User;
import io.github.chamikathereal.auction.ejb.exception.InvalidBidException;
import io.github.chamikathereal.auction.ejb.remote.AuctionManager;
import io.github.chamikathereal.auction.ejb.remote.BidManager;
import io.github.chamikathereal.auction.ejb.remote.UserSessionManager;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/place-bid")
public class PlaceBidServlet extends HttpServlet {

    @EJB
    private BidManager bidManager;

    @EJB
    private AuctionManager auctionManager;

    @EJB
    private UserSessionManager userSessionManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        User user = null;
        if (session != null) {
            user = userSessionManager.getUser(session.getId());
        }
        if (user == null) {
            System.out.println("[AUTH] No user in session, redirecting to login.");
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            double amount = Double.parseDouble(request.getParameter("amount"));

            Bid bid = new Bid(user.getName(), amount, itemId, user.getEmail());
            bid.setEmail(user.getEmail());

            System.out.printf("[BID][REQUEST] User '%s' is bidding $%.2f on item #%d%n", user.getName(), amount, itemId);

            bidManager.placeBid(bid);

            System.out.println("[BID][SUCCESS] Bid processed successfully, redirecting to home.");
            response.sendRedirect("home");

        } catch (InvalidBidException e) {
            System.out.printf("[BID][INVALID] %s%n", e.getMessage());
            request.setAttribute("error", e.getMessage());

            request.setAttribute("items", auctionManager.getItems());
            request.setAttribute("bids", auctionManager.getAllBids());

            request.getRequestDispatcher("/auction.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("[BID][ERROR] Invalid bid amount format.");
            request.setAttribute("error", "Invalid bid amount");
            request.getRequestDispatcher("/auction.jsp").forward(request, response);
        }
    }
}



