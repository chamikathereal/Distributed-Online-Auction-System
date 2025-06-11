<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, io.github.chamikathereal.auction.core.model.AuctionItem" %>

<%
    List<AuctionItem> items = (List<AuctionItem>) request.getAttribute("items");
    if (items != null) {
        for (AuctionItem item : items) {
%>
<div class="card mb-3 p-2">
    <div class="card-body">
        <p class="Inter-Bold mb-1"><strong><%= item.getName() %></strong></p>
        <p class="Inter-Bold mb-1"><%= item.getDescription() %></p>
        <p class="Inter-Bold mb-1">Current Bid: <strong class="current-bid" id="current-bid-<%= item.getId() %>">$<%= item.getStartingPrice() %></strong></p>
        <p class="Inter-Bold mb-1">
            Expires At: <span class="expires-at" data-expires="<%= item.getExpiresAt() %>" data-itemid="<%= item.getId() %>"></span>
        </p>
        <div class="col-12">
            <div class="row bid-form" id="bid-form-<%= item.getId() %>">
                <form action="place-bid" method="post">
                    <input type="hidden" name="itemId" value="<%= item.getId() %>"/>
                    <div class="col-12">
                        <input type="number" name="amount" step="0.01" required
                               class="input-border form-control" placeholder="Enter bid amount">
                    </div>
                    <div class="col-12 mt-3 mb-0">
                        <button type="submit" class="btn btn-primary btn-block w-100">Place Bid</button>
                    </div>
                </form>
            </div>
            <strong id="closed-msg-<%= item.getId() %>" class="text-danger mt-3 d-block d-none">Bidding Closed ❌</strong>
        </div>
    </div>
</div>
<%
    }
} else {
%>
<p class="text-white">No auction items currently available.</p>
<%
    }
%>
