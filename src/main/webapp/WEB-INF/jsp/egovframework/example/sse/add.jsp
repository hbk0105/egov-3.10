<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-04
  Time: 오후 3:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="frm" id="frm" action="/notifications/send-data/1" method="post" enctype="multipart/form-data">
    <input type="text" name="data" />
    <!-- ... -->
    <button type="button" onclick="fnSubmit()">addMsg</button>
</form>
<script src="/common/js/jquery-3.7.1.js"></script>
<script>

    function fnUuidv4() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            let r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }

    function fnSubmit(){


        $('#frm').attr('action','/notifications/send-data/1').submit();

    }



</script>
<div id="ajaxResult"></div>
</body>
</html>
