<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>PlexusBid - Admin Log In</title>
</head>

<body class="body-background">

<div class="container-fluid">
    <div class="col-12 d-flex justify-content-center align-items-center vh-100">

        <div class="card p-3" style="width: 25rem;">
            <div class="card-body">
                <h5 class="card-title Inter-Bold mb-4">👨🏻‍💻 Hello Admin!</h5>
                <p class="mt-2 mb-2">Log in details for testing.</p>
                <p class="mb-0">Email: admin@gmail.com</p>
                <p class="mb-3">Password: 1234</p>
                <form class="card-text" action="admin-login" method="post">
                    <div class="form-group mb-3">
                        <label>📧 Admin Email</label>
                        <input type="email" name="email" class="form-control" placeholder="Enter email" required>
                    </div>
                    <div class="form-group mb-4">
                        <label>🔒 Password</label>
                        <input type="password" name="password" class="form-control"
                               placeholder="*********" required>
                    </div>
                    <div class="form-group border d-flex justify-content-center">
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