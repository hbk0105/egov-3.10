<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/cmmn/header.jsp" %>

<div class="contact">
    <div class="container">
        <div class="section-header text-center">
            <p>Welcome</p>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="contact-info">
                     <h2>Quick Contact Info</h2>
                     <div class="contact-info-item">
                         <div class="contact-info-icon">
                             <i class="far fa-clock"></i>
                         </div>
                         <div class="contact-info-text">
                             <h3>Opening Hour</h3>
                             <p>Mon - Fri, 8:00 - 9:00</p>
                         </div>
                     </div>
                     <div class="contact-info-item">
                         <div class="contact-info-icon">
                             <i class="fa fa-phone-alt"></i>
                         </div>
                         <div class="contact-info-text">
                             <h3>Call Us</h3>
                             <p>+012 345 6789</p>
                         </div>
                     </div>
                     <div class="contact-info-item">
                         <div class="contact-info-icon">
                             <i class="far fa-envelope"></i>
                         </div>
                         <div class="contact-info-text">
                             <h3>Email Us</h3>
                             <p>info@example.com</p>
                         </div>
                     </div>
                 </div>
            </div>
            <div class="col-md-8">
                <div class="contact-form">
                    <div id="success"></div>
                    <form name="sentMessage" id="contactForm" novalidate="novalidate">
                        <div class="control-group">
                            <input type="text" class="form-control" id="name" placeholder="Your Name" required="required" data-validation-required-message="Please enter your name" aria-invalid="false">
                            <p class="help-block text-danger"></p>
                        </div>
                        <div class="control-group">
                            <input type="email" class="form-control" id="email" placeholder="Your Email" required="required" data-validation-required-message="Please enter your email">
                            <p class="help-block text-danger"></p>
                        </div>
                        <div class="control-group">
                            <input type="text" class="form-control" id="subject" placeholder="Subject" required="required" data-validation-required-message="Please enter a subject">
                            <p class="help-block text-danger"></p>
                        </div>
                        <div class="control-group">
                            <textarea class="form-control" id="message" placeholder="Message" required="required" data-validation-required-message="Please enter your message"></textarea>
                            <p class="help-block text-danger"></p>
                        </div>
                        <div>
                            <button class="btn btn-custom" type="submit" id="sendMessageButton">Send Message</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/jsp/cmmn/footer.jsp" %>
<!-- Template Javascript -->
<script src="/common/bootstrap/js/main.js"></script>