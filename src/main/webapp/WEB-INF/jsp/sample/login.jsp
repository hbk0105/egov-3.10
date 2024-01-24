<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/cmmn/header.jsp" %>
<link href="/common/www/css/sign.css" rel="stylesheet">
<link href="/css/validate/error.css" rel="stylesheet">
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
                                        <label for="userId" class="label">userId</label>
                                        <input id="userId" name="userId" type="text" class="input">
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
                                        <label for="userId" class="label">userId</label>
                                        <input id="userId" name="userId" type="text" class="input">
                                    </div>
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
        customRules = {
            username:  { required : true, regex : /^[가-힣]{2,10}$/ },
        };

        customMsgs = {
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
        //console.log(form);

        // 동적으로 생성된 폼 데이터
        let formData = {
            'name': 'John Doe',
            'email': 'john@example.com',
            // 원하는 데이터 필드를 추가할 수 있습니다.
        };
        // 동적으로 설정될 옵션
        let options = {
            'formName': 'dynamicForm1',
            'url': '/sample/submit.do',  // 실제 서버로 데이터를 보낼 URL을 입력하세요.
            'method': 'POST',  // 데이터 전송 방식 (POST 또는 GET)
        };

        // 동적으로 생성된 폼 데이터를 받아와서 submit
        fnDynamicFormData(options, formData);


    }

</script>