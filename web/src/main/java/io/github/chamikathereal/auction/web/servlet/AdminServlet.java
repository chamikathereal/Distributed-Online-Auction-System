package io.github.chamikathereal.auction.web.servlet;

import io.github.chamikathereal.auction.core.model.AuctionItem;
import io.github.chamikathereal.auction.core.model.Bid;
import io.github.chamikathereal.auction.ejb.remote.AuctionManager;
import io.github.chamikathereal.auction.web.websocket.WebSocketBroadcaster;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @EJB
    private AuctionManager auctionManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("isAdmin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        List<Bid> allBids = auctionManager.getAllBids();
        List<AuctionItem> allItems = auctionManager.getItems();

        request.setAttribute("bids", allBids);
        request.setAttribute("items", allItems);

        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String expireStr = request.getParameter("expires");

        if (name != null && description != null && priceStr != null && expireStr != null) {
            try {
                double price = Double.parseDouble(priceStr);
                int minutes = Integer.parseInt(expireStr);

                AuctionItem item = new AuctionItem();
                item.setName(name);
                item.setDescription(description);
                item.setStartingPrice(price);
                item.setExpiresAt(java.time.LocalDateTime.now().plusMinutes(minutes));

                auctionManager.addItem(item);

                // broadcast new item to all clients via WebSocket
                var wsMessage = Json.createObjectBuilder()
                        .add("type", "newItem")
                        .add("id", item.getId())
                        .add("name", item.getName())
                        .add("description", item.getDescription())
                        .add("startingPrice", item.getStartingPrice())
                        .add("expiresAt", item.getExpiresAt().toString())
                        .build();
                WebSocketBroadcaster.broadcast(wsMessage.toString());

                System.out.printf("[ADMIN][NEW ITEM] Broadcasted new item #%d: %s%n", item.getId(), item.getName());

            } catch (NumberFormatException e) {
                System.err.println("[ADMIN][ERROR] Invalid input for new item: " + e.getMessage());
            }
        }

        response.sendRedirect("admin");
    }
}
