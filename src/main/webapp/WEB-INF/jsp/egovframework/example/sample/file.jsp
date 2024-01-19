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
<form name="frm" id="frm" action="/uploadDynamicFiles.do" method="post" enctype="multipart/form-data">
    <input type="file" name="file1">
    <input type="file" name="file2">
    <!-- 동적으로 생성된 파일 필드들 -->
    <input type="file" name="dynamicFile3">
    <input type="file" name="dynamicFile4">
    <input type="file" name="dynamicFile5">

    <input type="text" name="CSRF_TOKEN" id="CSRF_TOKEN">
    <!-- ... -->
    <button type="button" onclick="fnSubmit()">Upload</button>
</form>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<%-- 공통 정의 함수--%>
<script src="/common/js/cmmonFn.js"></script>
<input type="text" class="form-control" id="inputMsg" name="inputMsg" value="<script>alert(1)</script>">
<button type="button" onclick="fnXss();">json xss test</button>
<script>

    function fnUuidv4() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            let r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }

    function fnSubmit(){
        let uuid = fnUuidv4();
        $('#CSRF_TOKEN').val(uuid);
        document.cookie = 'CSRF_TOKEN=' + uuid + ";path=/";

        //$('#frm').attr('action','/test.do').submit();
        //$('#frm').attr('action','/uploadDynamicFiles.do').submit();


        // AJAX 요청을 수행하는 함수 생성
        const dynamicAjax = fnCreateDynamicAjax();

        const dynamicOptions = {
            url: '/uploadDynamicFiles.do',
            method: 'POST',
            data: new FormData(document.getElementById('frm')),
            processData: false,  // 동적으로 설정 가능
            contentType:false,
        };

        // 콜백 함수 정의
        const myCallback = (response) => {
            // 동적으로 설정된 옵션으로 AJAX 요청을 보냈을 때의 콜백 동작
            console.log(response);
        };

        dynamicAjax(dynamicOptions,myCallback);


       /* var formData = new FormData(document.getElementById('frm'));
        $.ajax({
            type:"POST",
            enctype: 'multipart/form-data',
            processData:false,
            contentType:false,
            url:"/uploadDynamicFiles.do",
            data:formData,
            success:function(data){
                console.log("success")
            },error : function(request, status, error) {
                console.log("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
            }
        });*/

    }

    function fnXss(){

        // AJAX 요청을 수행하는 함수 생성
        let dynamicAjax = fnCreateDynamicAjax();

        let dynamicOptions = {
            url: '/ajax/List.do',
            dataType:'text'
        };

        // 콜백 함수 정의
        const myCallback = (response) => {
            // 동적으로 설정된 옵션으로 AJAX 요청을 보냈을 때의 콜백 동작
            console.log(response);
        };

        //dynamicAjax(dynamicOptions,myCallback);

        var params = {
            name: $('#inputMsg').val(),
            age: 30
        };

        var dataParam = {list:JSON.stringify(params), 'param1':'param1', 'param2':'param2'};

        dynamicOptions = {
            url: '/xss/jsonFilter.do',
            data       : dataParam,
            dataType: "json",
        };

        dynamicAjax(dynamicOptions, (response) => {
            $('#ajaxResult').html(response.param[0].name);
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
