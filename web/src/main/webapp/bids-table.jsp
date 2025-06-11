<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, io.github.chamikathereal.auction.core.model.Bid" %>

<table class="table text-white"> <%-- Added text-white for table text --%>
    <thead class="p">
    <tr>
        <th scope="col">#Item ID</th>
        <th scope="col">Bidder Name</th>
        <th scope="col">Email</th>
        <th scope="col">Amount</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Bid> bids = (List<Bid>) request.getAttribute("bids");
        if (bids != null && !bids.isEmpty()) { // Check if bids list is not null and not empty
            for (Bid bid : bids) {
    %>
    <tr>
        <td><%= bid.getItemId() %></td>
        <td><%= bid.getBidderName() %></td>
        <td><%= bid.getEmail() %></td>
        <td>$<%= String.format("%.2f", bid.getAmount()) %></td> <%-- Formatted amount --%>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4" class="text-center">No bids yet.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>