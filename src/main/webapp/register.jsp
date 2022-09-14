<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Register</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md py-3">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="#"><span
            class="bs-icon-sm bs-icon-rounded bs-icon-semi-white d-flex justify-content-center align-items-center me-2 bs-icon">
        </span><span>Brand</span></a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link active" href="#">First Item</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Second Item</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Third Item</a></li>
            </ul>
            <button class="btn btn-light" type="button">Button</button>
            <button class="btn btn-dark" type="button">Button</button>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col">
            <section class="position-relative py-4 py-xl-5">
                <div class="container">
                    <div class="row d-flex justify-content-center">
                        <div class="col-md-6 col-xl-4">
                            <div class="card mb-5">
                                <div class="card-body d-flex flex-column align-items-center">
                                    <form class="text-center" method="post">
                                        <div class="d-lg-flex justify-content-lg-end align-items-lg-start mb-3"><input
                                                class="form-control" type="email" name="email" placeholder="Email"
                                                minlength="3" maxlength="44" pattern="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$"
                                                required=""></div>
                                        <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                                                 placeholder="First name"
                                                                                 name="first_name"
                                                                                 pattern="\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"
                                                                                 minlength="1" required=""
                                                                                 maxlength="44"></div>
                                        <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                                                 placeholder="Last name"
                                                                                 name="last_name" minlength="1"
                                                                                 pattern="\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"
                                                                                 required="" maxlength="44"></div>
                                        <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                                                 name="phone" placeholder="Phone number"
                                                                                 maxlength="44" required=""
                                                                                 minlength="4"
                                                                                 pattern="^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$">
                                        </div>
                                        <div class="mb-3"><input class="form-control" type="password" name="password"
                                                                 placeholder="Password" required="" minlength="8"
                                                                 pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$">
                                        </div>
                                        <div class="mb-3">
                                            <button class="btn btn-success d-block w-100" type="submit">Register
                                            </button>
                                        </div>
                                        <p class="text-muted">Already have an account?</p>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
<footer class="text-center">
    <div class="container text-muted py-4 py-lg-5">
        <ul class="list-inline">
            <li class="list-inline-item me-4"><a class="link-secondary" href="#">Web design</a></li>
            <li class="list-inline-item me-4"><a class="link-secondary" href="#">Development</a></li>
            <li class="list-inline-item"><a class="link-secondary" href="#">Hosting</a></li>
        </ul>
        <p class="mb-0">Copyright © 2022 Brand</p>
    </div>
</footer>
<script src="js/bootstrap.min.js"></script>
</body>

</html>