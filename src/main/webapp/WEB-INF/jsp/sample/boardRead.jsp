<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/cmmn/header.jsp" %>
<link href="/css/validate/siupError.css" rel="stylesheet">
<div class="contact">
    <div class="container">
        <div class="section-header text-center">
            <p>Notice</p>
            <h2>Ace Car Wash</h2>
        </div>

        <div class="comment-form">
            <h2>Board Read Page</h2>
            <form name="board" id="board">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${paramMap.pageIndex}">
                <input type="hidden" name="id" id="id" value="${paramMap.id}">
                <div class="form-group">
                    <label for="title">TITLE *</label>
                    <input type="text" class="form-control" readonly value="${result.TITLE}" id="title">
                </div>

                <div class="form-group">
                    <label for="content">CONTENT *</label>
                    <textarea id="content" readonly name="content" cols="30" rows="5" class="form-control">${result.CONTENT}</textarea>
                </div>


                <div class="form-group mb-lg-5">
                    <label>FILE</label>
                    <c:choose>
                        <c:when test="${fn:length(fileList) > 0}">
                            <ul class="list-group">
                                <c:forEach var="result" items="${fileList}" varStatus="st">
                                    <li class="list-group-item">
                                        <a href="#" onclick="fnFileDownLoad('${result.ID}','${result.BOARD_ID}')">${result.ORIGINAL_FILE_NAME}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                    </c:choose>
                </div>
                <div class="form-group">
                    <button type="button" onclick="fnList();" class="btn btn-outline-dark">LIST</button>
                    <button type="button" onclick="fnModify();" class="btn btn-outline-primary">MODIFY</button>
                </div>
            </form>
        </div>

    </div>


</div>
<%@ include file="/WEB-INF/jsp/cmmn/footer.jsp" %>
<script src="/js/cmmonFn.js"></script>
<%--
<link rel="stylesheet" type="text/css" media="screen" href="/common/bootstrap/css/bootstrap.min.css">--%>

<script src="/common/www/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function fnList(){
        $('#pageIndex').val(1);
        $('#board').attr('action','/sample/boardList.do').submit();
    }

    function fnModify(){
        $('#board').attr('action','/sample/boardWrite.do').submit();
    }

</script>