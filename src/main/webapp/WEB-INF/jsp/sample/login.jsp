<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/cmmn/header.jsp" %>

<style>

    body{
        margin:0;
        color:#6a6f8c;
    }
    *,:after,:before{box-sizing:border-box}
    .clearfix:after,.clearfix:before{content:'';display:table}
    .clearfix:after{clear:both;display:block}
    a{color:inherit;text-decoration:none}

    .login-wrap{
        width:100%;
        margin:auto;
        max-width:525px;
        min-height:670px;
        position:relative;
        background:url(https://raw.githubusercontent.com/khadkamhn/day-01-login-form/master/img/bg.jpg) no-repeat center;
        box-shadow:0 12px 15px 0 rgba(0,0,0,.24),0 17px 50px 0 rgba(0,0,0,.19);
    }
    .login-html{
        width:100%;
        height:100%;
        position:absolute;
        padding:90px 70px 50px 70px;
        background:rgba(40,57,101,.9);
    }
    .login-html .sign-in-htm,
    .login-html .sign-up-htm{
        top:0;
        left:0;
        right:0;
        bottom:0;
        position:absolute;
        transform:rotateY(180deg);
        backface-visibility:hidden;
        transition:all .4s linear;
    }
    .login-html .sign-in,
    .login-html .sign-up,
    .login-form .group .check{
        display:none;
    }
    .login-html .tab,
    .login-form .group .label,
    .login-form .group .button{
        text-transform:uppercase;
    }
    .login-html .tab{
        font-size:22px;
        margin-right:15px;
        padding-bottom:5px;
        margin:0 15px 10px 0;
        display:inline-block;
        border-bottom:2px solid transparent;
    }
    .login-html .sign-in:checked + .tab,
    .login-html .sign-up:checked + .tab{
        color:#fff;
        border-color:#1161ee;
    }
    .login-form{
        min-height:345px;
        position:relative;
        perspective:1000px;
        transform-style:preserve-3d;
    }
    .login-form .group{
        margin-bottom:15px;
    }
    .login-form .group .label,
    .login-form .group .input,
    .login-form .group .button{
        width:100%;
        color:#fff;
        display:block;
    }
    .login-form .group .input,
    .login-form .group .button{
        border:none;
        padding:15px 20px;
        border-radius:25px;
        background:rgba(255,255,255,.1);
    }
    .login-form .group input[data-type="password"]{
        text-security:circle;
        -webkit-text-security:circle;
    }
    .login-form .group .label{
        color:#aaa;
        font-size:12px;
    }
    .login-form .group .button{
        background:#1161ee;
    }
    .login-form .group label .icon{
        width:15px;
        height:15px;
        border-radius:2px;
        position:relative;
        display:inline-block;
        background:rgba(255,255,255,.1);
    }
    .login-form .group label .icon:before,
    .login-form .group label .icon:after{
        content:'';
        width:10px;
        height:2px;
        background:#fff;
        position:absolute;
        transition:all .2s ease-in-out 0s;
    }
    .login-form .group label .icon:before{
        left:3px;
        width:5px;
        bottom:6px;
        transform:scale(0) rotate(0);
    }
    .login-form .group label .icon:after{
        top:6px;
        right:0;
        transform:scale(0) rotate(0);
    }
    .login-form .group .check:checked + label{
        color:#fff;
    }
    .login-form .group .check:checked + label .icon{
        background:#1161ee;
    }
    .login-form .group .check:checked + label .icon:before{
        transform:scale(1) rotate(45deg);
    }
    .login-form .group .check:checked + label .icon:after{
        transform:scale(1) rotate(-45deg);
    }
    .login-html .sign-in:checked + .tab + .sign-up + .tab + .login-form .sign-in-htm{
        transform:rotate(0);
    }
    .login-html .sign-up:checked + .tab + .login-form .sign-up-htm{
        transform:rotate(0);
    }

    .hr{
        height:2px;
        margin:60px 0 50px 0;
        background:rgba(255,255,255,.2);
    }
    .foot-lnk{
        text-align:center;
        color: white;
    }
</style>
<div class="contact">
    <div class="container">
        <div class="section-header text-center">
            <p>Welcome</p>
            <h2>Login</h2>
        </div>
        <div class="row">
            <div class="col-md-4">
               <%-- <div class="contact-info">
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
                </div>--%>
            </div>
            <div class="col-md-12">
                <%--<div class="contact-form">
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

                            &lt;%&ndash;<button type="button" class="btn btn-primary">Primary</button>
                            <button type="button" class="btn btn-secondary">Secondary</button>
                            <button type="button" class="btn btn-success">Success</button>
                            <button type="button" class="btn btn-danger">Danger</button>
                            <button type="button" class="btn btn-warning">Warning</button>
                            <button type="button" class="btn btn-info">Info</button>
                            <button type="button" class="btn btn-light">Light</button>
                            <button type="button" class="btn btn-dark">Dark</button>

                            <button type="button" class="btn btn-link">Link</button>&ndash;%&gt;
                        </div>
                    </form>
                </div>--%>

                    <div class="login-wrap">
                        <div class="login-html">
                            <input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
                            <input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
                            <div class="login-form">
                                <div class="sign-in-htm">
                                    <div class="group">
                                        <label for="username" class="label">username</label>
                                        <input id="username" name="username" type="text" class="input">
                                    </div>
                                    <div class="group">
                                        <label for="password" class="label">Password</label>
                                        <input id="password" name="Password" type="password" class="input" data-type="password">
                                    </div>
                                    <div class="group">
                                        <input id="check" type="checkbox" class="check" checked>
                                        <label for="check"><span class="icon"></span> Keep me Signed in</label>
                                    </div>
                                    <div class="group">
                                        <button type="button" class="button" value="Sign Up">Sign In</button>
                                    </div>
                                    <div class="hr"></div>
                                    <div class="foot-lnk">
                                        <a href="#forgot">Forgot Password?</a>
                                    </div>
                                </div>
                                <div class="sign-up-htm">
                                    <div class="group">
                                        <label for="username" class="label">Username</label>
                                        <input id="username" name="username" type="text" class="input">
                                    </div>
                                    <div class="group">
                                        <label for="password" class="label">Password</label>
                                        <input id="password" name="password" type="password" class="input" data-type="password">
                                    </div>
                                    <div class="group">
                                        <label for="repeatPassword" class="label">Repeat Password</label>
                                        <input id="repeatPassword" name="repeatPassword" type="password" class="input" data-type="password">
                                    </div>
                                    <div class="group">
                                        <label for="email" class="label">Email Address</label>
                                        <input id="email" name="email" type="text" class="input">
                                    </div>
                                    <div class="group">
                                        <button type="button" class="button" value="Sign Up">Sign Up</button>
                                    </div>
                                    <div class="foot-lnk">
                                        <a href="#Already">Already Member?</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>








      <%--  <div class="container mt-3">
            <h2>Basic Table</h2>
            <p>The .table class adds basic styling (light padding and horizontal dividers) to a table:</p>
            <table class="table">
                <thead>
                <tr>
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>John</td>
                    <td>Doe</td>
                    <td>john@example.com</td>
                </tr>
                <tr>
                    <td>Mary</td>
                    <td>Moe</td>
                    <td>mary@example.com</td>
                </tr>
                <tr>
                    <td>July</td>
                    <td>Dooley</td>
                    <td>july@example.com</td>
                </tr>
                </tbody>
            </table>
        </div>--%>

    </div>
</div>


<%@ include file="/WEB-INF/jsp/cmmn/footer.jsp" %>
<!-- Template Javascript -->
<script src="/common/bootstrap/js/main.js"></script>