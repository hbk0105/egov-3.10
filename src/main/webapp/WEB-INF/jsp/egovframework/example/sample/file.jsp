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
<form action="/uploadDynamicFiles.do" method="post" enctype="multipart/form-data">
    <input type="file" name="file1">
    <input type="file" name="file2">
    <!-- 동적으로 생성된 파일 필드들 -->
    <input type="file" name="dynamicFile3">
    <input type="file" name="dynamicFile4">
    <input type="file" name="dynamicFile5">
    <!-- ... -->
    <button type="submit">Upload</button>
</form>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>

<input type="text" class="form-control" id="inputMsg" name="inputMsg" value="<script>alert(1)</script>">
<button type="button" onclick="fnXss();">json xss test</button>
<script>
    function fnXss(){

        var params = {
            "name": $('#inputMsg').val(),
            "age": 30
        };


        var dataParam = {list:JSON.stringify(params), param1:'param1', param2:'param2'};


        $.ajax({
            type       : "POST",
            url        : "/xss/jsonFilter.do",
            data       : dataParam,
            dataType: "json",
            success: function(data) {
                console.log(data);
               $('#ajaxResult').html(xssFilter(data.param[0].name));
            },
            error : function(request, status, error) {
                console.log("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
            }
        });

    }

    // XSS FILTER
    function xssFilter(obj){
        obj = obj.replaceAll("<", "&lt;");
        obj = obj.replaceAll(">", "&gt;");
        obj = obj.replaceAll("\\(", "&#40;");
        obj = obj.replaceAll("\\)", "&#41;");
        obj = obj.replaceAll("'", "&#x27;");
        obj = obj.replaceAll("\x3C","&lt;");
        obj = obj.replaceAll('"',"&quot;");
        obj = obj.replaceAll('&',"&amp;");
        return obj;
    }

    function XSSCheck(str, level) {
        if (level == undefined || level == 0) {
            str = str.replace(/\<|\>|\"|\'|\%|\;|\(|\)|\&|\+|\-/g,"");
        } else if (level != undefined && level == 1) {
            str = str.replace(/\</g, "&lt;");
            str = str.replace(/\>/g, "&gt;");
        }
        return str;
    }

</script>
<div id="ajaxResult"></div>
</body>
</html>
