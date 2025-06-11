<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, io.github.chamikathereal.auction.core.model.Bid, io.github.chamikathereal.auction.core.model.AuctionItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>PlexusBid - Admin</title>
</head>

<body class="body-background">

<div class="container-fluid">
    <div class="row">
        <div class="col-12 d-flex justify-content-center">
            <div class="col-10">
                <div class="col-12 mt-5 mb-5 d-flex justify-content-center align-items-center">
                    <div class="h1 text-white">Admin Panel 🛠️</div>
                </div>

                <div class="col-12">
                    <div class="row">
                        <div class="col-12 col-md-6">
                            <div class="card d-flex justify-content-center align-items-center">
                                <div class="card-body mt-3 mb-3">
                                    <h5 class="card-title mb-4">Add New Auction Item. 🛒</h5>
                                    <form action="admin" method="post">
                                        <div class="form-row">
                                            <div class="form-group col-md-12 mb-2">
                                                <label class="mb-1 Inter-Regular">Item Name:</label>
                                                <input type="text" name="name" required
                                                       class="input-border form-control">
                                            </div>
                                            <div class="form-group col-md-12 mb-2">
                                                <label class="mb-1 Inter-Regular">Description:</label>
                                                <textarea name="description" rows="4" cols="40" required
                                                          class="input-border form-control"></textarea>
                                            </div>
                                            <div class="form-group col-md-12 mb-2">
                                                <label class="mb-1 Inter-Regular">Starting Price ($):</label>
                                                <input type="number" name="price" step="0.01" required
                                                       class="input-border form-control">
                                            </div>
                                            <div class="form-group col-md-12 mb-2">
                                                <label class="mb-1 Inter-Regular">Expire After (minutes):</label>
                                                <input type="number" name="expires" required min="1"
                                                       class="input-border form-control">
                                            </div>
                                        </div>
                                        <button type="submit" class="btn btn-primary mt-3">Add Item</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 col-md-6 ">
                            <div class="row">
                                <div class="col-12 mb-2">
                                    <div class="h1 text-white">All Bids 🧾</div>
                                </div>
                                <div class="col-12">
                                    <div id="bids-container">
                                        <%-- This is where bids-table.jsp will be included --%>
                                        <jsp:include page="bids-table.jsp"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12 d-flex justify-content-center align-items-center mt-5 mb-5">
                    <div class="row">
                        <div class="col-12 mt-5 mb-5 d-flex justify-content-center align-items-center">
                            <div class="h1 text-white">Current Auction Items 📦</div>
                        </div>
                        <div class="col-12">
                            <table class="table rounded text-white"> <%-- Added text-white here --%>
                                <thead class="p">
                                <tr>
                                    <th scope="col">#ID</th>
                                    <th scope="col">Item Name</th>
                                    <th scope="col">Description</th>
                                    <th scope="col">Price ($)</th>
                                    <th scope="col">Expires At</th> <%-- Changed from "Expire After (minutes)" --%>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    List<AuctionItem> items = (List<AuctionItem>) request.getAttribute("items");
                                    if (items != null && !items.isEmpty()) {
                                        for (AuctionItem item : items) {
                                %>
                                <tr>
                                    <th scope="row"><%= item.getId() %></th>
                                    <td><%= item.getName() %></td>
                                    <td><%= item.getDescription() %></td>
                                    <td>$<%= String.format("%.2f", item.getStartingPrice()) %></td>
                                    <td><%= item.getExpiresAt() %></td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="5" class="text-center">No auction items currently available.</td>
                                </tr>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
        crossorigin="anonymous"></script>
<script src="assets/js/auction.js"></script>

</body>
</html>