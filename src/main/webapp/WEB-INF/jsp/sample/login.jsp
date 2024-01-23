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
       /* max-width:525px;*/
        min-height:750px;
        position:relative;
        background:url(https://raw.githubusercontent.com/khadkamhn/day-01-login-form/master/img/bg.jpg) no-repeat center;
        background-size: cover;
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
        cursor: pointer;
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
        color:#fff;
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



    div.message{
        background: transparent url(msg_arrow.gif) no-repeat scroll left center;
        padding-left: 7px;
    }

    div.error{
        background-color:rgb(243 7 7 / 10%;
        border-color: #924949;
        border-style: solid solid solid none;
        border-width: 2px;
        padding: 5px;
    }
</style>
<div class="contact">
    <div class="container">
        <div class="section-header text-center">
            <p>Welcome</p>
            <h2>SIGN IN & SIGN UP</h2>
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
                <div class="login-wrap">
                    <div class="login-html">
                        <input id="tab-1" type="radio" text="Sign In" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
                        <input id="tab-2" type="radio" text="Sign Up" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
                        <div class="login-form">
                            <form name="signIn" id="signIn" onsubmit="return false;">
                                <div class="sign-in-htm">
                                    <div class="group">
                                        <label for="name" class="label">name</label>
                                        <input id="name" name="name" type="text" class="input">
                                    </div>
                                    <div class="group">
                                        <label for="password" class="label">password</label>
                                        <input id="password" name="password" type="password" class="input" data-type="password">
                                    </div>
                                    <div class="group">
                                        <input id="check" type="checkbox" class="check" checked>
                                        <label for="check"><span class="icon"></span> Keep me Signed in</label>
                                    </div>
                                    <div class="group">
                                        <button type="button" id="btnSignIn" class="button" value="Sign Up">Sign In</button>
                                    </div>
                                    <div class="hr"></div>
                                    <div class="foot-lnk">
                                        <a href="#forgot">Forgot Password?</a>
                                    </div>
                                </div>
                            </form>
                            <form name="signUp" id="signUp" onsubmit="return false;">
                                <div class="sign-up-htm">
                                    <div class="group">
                                        <label for="username" class="label">name</label>
                                        <input id="username" name="username" type="text" class="input">
                                    </div>
                                    <div class="group">
                                        <label for="passwordCh" class="label">password</label>
                                        <input id="passwordCh" name="passwordCh" type="password" class="input" data-type="password">
                                    </div>
                                    <div class="group">
                                        <label for="passwordVerify" class="label">Password Verify</label>
                                        <input id="passwordVerify" name="passwordVerify" type="password" class="input" data-type="password">
                                    </div>
                                    <div class="group">
                                        <label for="email" class="label">Email Address</label>
                                        <input id="email" name="email" type="text" class="input">
                                    </div>
                                    <div class="group">
                                        <button type="button" id="btnSignUp" class="button" value="Sign Up">Sign Up</button>
                                    </div>
                                    <div class="foot-lnk">
                                        <a href="#Already">Already Member?</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/cmmn/footer.jsp" %>
<%--
<link rel="stylesheet" type="text/css" media="screen" href="/common/bootstrap/css/bootstrap.min.css">--%>
<script src="/common/www/bootstrap/js/bootstrap.min.js"></script>

<script src="/js/validate/validate.js" ></script>
<script src="/js/validate/validateMin.js" ></script>
<script src="/js/validate/customModule.js" ></script>

<script type="text/javascript">

    $(function(){
/*
        $('.login-wrap').css('min-height','600px');

        $('.sign-in').on('click',function(i){
            $('.login-wrap').css('min-height','600px');
        });

        $('.sign-up').on('click',function(i){
            $('.login-wrap').css('min-height','750px');
        });
*/

        customModule.init($('#signIn'),null,null,fnSubmit);


        // 사용 예시:
        const customRules = {
            username:  { required : true, regex : /^[가-힣]{2,10}$/ },
        };

        const customMsgs = {
            username: {required:"이름을 입력해주세요.", regex : "한글 2글자 이상 10글자 미만으로 작성하세요."},
        };

        //customModule.init($('#signUp'),null,null,fnSubmit);

        customModule.updateDefaultRule($('#signUp'),customRules,customMsgs,fnSubmit);

        $("#btnSignIn").click(function(){
            $("#signIn").submit();
        });

        $("#btnSignUp").click(function(){
            $("#signUp").submit();
        });
    });



    function fnSubmit(form){
        console.log(form);
    }

</script>