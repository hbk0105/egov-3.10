<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/cmmn/header.jsp" %>
<link href="/css/validate/error.css" rel="stylesheet">
<style>
    .filebox .upload-name {
        display: inline-block;
        height: 40px;
        padding: 0 10px;
        vertical-align: middle;
        border: 1px solid #dddddd;
        width: 50%;
        color: #999999;
    }

    .filebox label {
        display: inline-block;
        padding: 10px 20px;
        color: #fff;
        vertical-align: middle;
        background-color: #999999;
        cursor: pointer;
        height: 40px;
        margin-left: 10px;
    }

    .filebox input[type="file"] {
        position: absolute;
        width: 0;
        height: 0;
        padding: 0;
        overflow: hidden;
        border: 0;
    }

</style>
<div class="contact">
    <div class="container">
        <div class="section-header text-center">
            <p>Notice</p>
            <h2>Ace Car Wash</h2>
        </div>

        <div class="comment-form">
            <h2>Board Read Page</h2>
            <form name="board" id="board" action="/sample/boardExec.do" method="post" enctype="multipart/form-data">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${paramMap.pageIndex}">
                <input type="hidden" name="id" id="id" value="${paramMap.id}">
                <div class="form-group">
                    <label for="title">TITLE *</label>
                    <input type="text" name="title" class="form-control" value="${result.TITLE}" id="title">
                </div>

                <div class="form-group">
                    <label for="content">CONTENT *</label>
                    <textarea id="content" name="content" cols="30" rows="5" class="form-control">${result.CONTENT}</textarea>
                </div>


                <div class="form-group" STYLE="margin-bottom: 0px;">
                    <label>FILE *</label>
                </div>

                <div class="form-group mb-lg-5 filebox">
                    <c:choose>
                        <c:when test="${fn:length(fileList) > 0}">

                        </c:when>
                        <c:otherwise>
                            <input class="upload-name" value="첨부파일" placeholder="첨부파일">
                            <label for="file" style="margin-bottom: 0px;">파일찾기</label>
                            <input type="file" name="file" id="file">
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="form-group">
                    <button type="button" onclick="fnList();" class="btn btn-outline-dark">LIST</button>
                    <button type="button" id="btnSave" class="btn btn-outline-primary">SAVE</button>
                </div>
            </form>
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

    function fnList(){
        $('#pageIndex').val(1);
        $('#board').attr('action','/sample/boardList.do').submit();
    }


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

        customModule.updateDefaultRule($('#board'),customRules,customMsgs,null);

        $("#btnSave").click(function(){
            $("#board").submit();
        });

        $("#file").on('change',function(){
            var fileName = $("#file").val();
            $(".upload-name").val(fileName);
        });

    });


</script>