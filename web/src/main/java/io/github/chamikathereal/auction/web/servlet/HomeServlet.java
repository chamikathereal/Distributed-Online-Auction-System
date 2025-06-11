package io.github.chamikathereal.auction.web.servlet;

import io.github.chamikathereal.auction.core.model.AuctionItem;
import io.github.chamikathereal.auction.core.model.Bid;
import io.github.chamikathereal.auction.ejb.remote.AuctionManager;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @EJB
    private AuctionManager auctionManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AuctionItem> items = auctionManager.getItems();
        List<Bid> bids = auctionManager.getAllBids();

        request.setAttribute("items", items);
        request.setAttribute("bids", bids);

        String ajaxType = request.getParameter("ajax");
        if ("items".equals(ajaxType)) {
            request.getRequestDispatcher("/auction-items.jsp").forward(request, response);
        } else if ("bids".equals(ajaxType)) {
            request.getRequestDispatcher("/bids-table.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/auction.jsp").forward(request, response);
        }


    }
}

