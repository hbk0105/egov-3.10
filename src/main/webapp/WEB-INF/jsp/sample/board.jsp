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
            <p>Notice</p>
        </div>
        <div class="row">
            <div class="container mt-3">
                <h2>Ace Car Washe</h2>
                <table class="table">
                    <thead>
                    <tr>
                        <th>No</th>
                        <th>Title</th>
                        <th>Create Date</th>
                        <th>View Count</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
                <%@ include file="/WEB-INF/jsp/cmmn/paging.jsp"%>
            </div>
            <%-- container mt-3--%>
        </div>
        <%-- row --%>
        <button type="button" id="fnWrite" style="float: right;" class="btn btn-custom" value="Sign Up">Write</button>
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


        // 사용 예시:
        customRules = {
            title:  { required : true },
            content:{ required : true},
        };

        customMsgs = {
            title: {required:"제목을 입력해주세요."},
            content:{ required : "내용을 입력해주세요."},
        };

        customModule.updateDefaultRule($('#signUp'),customRules,customMsgs,fnSubmit);


        $("#btnSignIn").click(function(){
            $("#signIn").submit();
        });

        $("#btnSignUp").click(function(){
            $("#signUp").submit();
        });
    });


</script>