<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, io.github.chamikathereal.auction.core.model.AuctionItem" %>
<%@ page import="io.github.chamikathereal.auction.core.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>PlexusBid - Auction</title>
</head>

<body class="body-background">
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<div class="container-fluid">
    <div class="row">
        <div class="col-12 d-flex justify-content-center">
            <div class="col-10">
                <div class="col-12 mt-5 mb-5 d-flex justify-content-center align-items-center">
                    <div class="h1 text-white">Live Auction 🛒</div>
                </div>
                <div class="col-12 mb-5">
                    <%-- Dynamic user info from original JSP --%>
                    <p class="text-white mb-0">Logged in as: <strong><%= user.getName() %>
                    </strong></p>
                    <p class="text-white mb-0"><%= user.getEmail() %>
                    </p>
                </div>

                <div class="col-12">
                    <div class="row">
                        <div class="col-12 col-md-6 mb-5">
                            <div class="card mb-5 d-flex justify-content-center align-items-center">
                                <div class="card-body mt-3">
                                    <h1 class="card-title mb-4">Live Auction Items 🛒</h1>
                                </div>
                                <div id="items-container" class="col-12"> <%-- Added col-12 here --%>
                                    <%-- This is where auction-items.jsp will be included --%>
                                    <jsp:include page="auction-items.jsp"/>
                                </div>
                                <% if (request.getAttribute("error") != null) { %>
                                <script>
                                    alert("<%= request.getAttribute("error") %>");
                                </script>
                                <% } %>
                            </div>
                        </div>

                        <div class="col-12 col-md-6">
                            <div class="row">
                                <div class="col-12 mb-2">
                                    <div class="h1 text-white">Live Bids 💸</div>
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
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
        crossorigin="anonymous"></script>

<!-- Only keep this if you use it for form focus/UX -->
<script>
    function bindFocusHandlers() {
        document.querySelectorAll('input[name="bidder"]').forEach(input => {
            input.addEventListener('focusin', () => input.dataset.typing = "true");
            input.addEventListener('focusout', () => input.dataset.typing = "false");
        });
        document.querySelectorAll('input[name="amount"]').forEach(input => {
            input.addEventListener('focusin', () => input.dataset.typing = "true");
            input.addEventListener('focusout', () => input.dataset.typing = "false");
        });
    }

    document.addEventListener('DOMContentLoaded', () => {
        bindFocusHandlers();
    });
</script>

<script src="assets/js/auction.js"></script>
<script>
    function pad(num) {
        return num < 10 ? "0" + num : num;
    }

    function updateCountdowns() {
        const now = new Date();
        document.querySelectorAll('.expires-at').forEach(function (span) {
            const expiresAtStr = span.getAttribute('data-expires');
            const itemId = span.getAttribute('data-itemid');
            if (!expiresAtStr || !itemId) return;
            const expiresAt = new Date(expiresAtStr);
            const diff = expiresAt - now;

            const bidForm = document.getElementById("bid-form-" + itemId);
            const closedMsg = document.getElementById("closed-msg-" + itemId);

            if (diff > 0) {
                const hours = Math.floor(diff / (1000 * 60 * 60));
                const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((diff % (1000 * 60)) / 1000);
                span.textContent = pad(hours) + ":" + pad(minutes) + ":" + pad(seconds) + " left";
                if (bidForm) bidForm.classList.remove("d-none");
                if (closedMsg) closedMsg.classList.add("d-none");
            } else {
                span.textContent = "Expired";
                if (bidForm) bidForm.classList.add("d-none");
                if (closedMsg) closedMsg.classList.remove("d-none");
            }
        });
    }

    updateCountdowns();
    setInterval(updateCountdowns, 1000);
</script>


</body>
</html>