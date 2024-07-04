<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
<%@ taglib prefix="encryp" uri="/WEB-INF/tlds/AESEncryption.tld"%>
개인정보 암복호화 tld 예제
<encryp:AESEncryp value="Hello, AES Encryption!" mode="enc" var="encryptedData"/>
Encrypted Data: ${encryptedData}
<br><br>
<encryp:AESEncryp value="${encryptedData}" mode="dec" var="decryptedData"/>
Decrypted Data: ${decryptedData}
--%>

<%-- 마스킹 예제
<%@ taglib prefix="mask" uri="/WEB-INF/tlds/MaskingTag.tld"%>
<mask:masking value="오바마" se="name" var="maskVar"/>
${maskVar}
--%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>Ace Car Wash</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free Website Template" name="keywords">
    <meta content="Free Website Template" name="description">


    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>



    <!-- Favicon -->
<%--    <link rel="shortcut icon" href="#">--%>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Barlow:wght@400;500;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- CSS Libraries -->
    <link href="/common/www/bootstrap/css/bootstrap-4.4.1.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="/common/www/bootstrap/lib/flaticon/font/flaticon.css" rel="stylesheet">
    <link href="/common/www/bootstrap/lib/animate/animate.min.css" rel="stylesheet">
    <link href="/common/www/bootstrap/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/common/www/bootstrap/css/style.css" rel="stylesheet">
</head>
<body>


<!-- Top Bar Start -->
<div class="top-bar">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-4 col-md-12">
                <div class="logo">
                    <a href="/sample/main.do">
                        <h1>Ace<span> Car Wash</span></h1>
                        <!-- <img src="/common/bootstrap/img/logo.jpg" alt="Logo"> -->
                    </a>
                </div>
            </div>
            <div class="col-lg-8 col-md-7 d-none d-lg-block">
                <div class="row">
                    <div class="col-4">
                        <div class="top-bar-item">
                            <div class="top-bar-icon">
                                <i class="far fa-clock"></i>
                            </div>
                            <div class="top-bar-text">
                                <h3>Opening Hour</h3>
                                <p>Mon - Sun, 8:00 - 18:00</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="top-bar-item">
                            <div class="top-bar-icon">
                                <i class="fa fa-phone-alt"></i>
                            </div>
                            <div class="top-bar-text">
                                <h3>Call Us</h3>
                                <p>+012 345 6789</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="top-bar-item">
                            <div class="top-bar-icon">
                                <i class="far fa-envelope"></i>
                            </div>
                            <div class="top-bar-text">
                                <h3>Email Us</h3>
                                <p>info@example.com</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Top Bar End -->



<!-- Nav Bar Start -->
<div class="nav-bar">
    <div class="container">
        <nav class="navbar navbar-expand-lg bg-dark navbar-dark">
            <a href="javascript:void(0)" class="navbar-brand">MENU</a>
            <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                <div class="navbar-nav mr-auto">
                    <a href="javascript:void(0)" class="nav-item nav-link active">Home</a>
                    <a href="javascript:void(0)" class="nav-item nav-link">About</a>
                    <a href="javascript:void(0)" class="nav-item nav-link">Service</a>
                    <a href="javascript:void(0)" class="nav-item nav-link">Price</a>
                    <a href="javascript:void(0)" class="nav-item nav-link">Washing Points</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Pages</a>
                        <div class="dropdown-menu">
                            <a href="javascript:void(0)" class="dropdown-item">Blog Grid</a>
                            <a href="javascript:void(0)" class="dropdown-item">Detail Page</a>
                            <a href="javascript:void(0)" class="dropdown-item">Team Member</a>
                            <a href="javascript:void(0)" class="dropdown-item">Schedule Booking</a>
                        </div>
                    </div>
                    <a href="/sample/boardList.do" class="nav-item nav-link">Notice</a>
                </div>
                <div class="ml-auto">
                    <a class="btn btn-custom" href="/sample/login.do">Sing In/Up</a>
                </div>
            </div>
        </nav>
    </div>
</div>
<!-- Nav Bar End -->


