<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>PlexusBid - Bidder Log In</title>
</head>

<body class="body-background">

<div class="container-fluid">
    <div class="col-12 d-flex justify-content-center align-items-center vh-100">

        <div class="card p-3" style="width: 25rem;">
            <div class="card-body">
                <h5 class="card-title Inter-Bold mb-4">👨🏻‍💻 Hello Bidders!</h5>
                <form class="card-text" action="login" method="post">
                    <div class="form-group mb-3">
                        <label>📧 Enter Email</label>
                        <input type="email" name="email" class="form-control" placeholder="johndoe@gmail.com" required>
                    </div>
                    <div class="form-group mb-4">
                        <label>👤 Enter User Name</label>
                        <input type="text" name="name" class="form-control" placeholder="john_doe" required>
                    </div>
                    <div class="form-group border d-flex justify-content-center">
                        <%-- Changed from <a> to <button type="submit"> to align with form submission --%>
                        <button type="submit" class="btn btn-primary w-100 btn-block">Sign In</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
        crossorigin="anonymous"></script>
</body>
</html>