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
    .container.mt-3{
        min-height: 350px;
    }
</style>
<div class="contact">
    <div class="container">
        <div class="section-header text-center">
            <p>Notice</p>
            <h2>Ace Car Wash</h2>
        </div>

        <div class="container mt-3">
            <form name="board" id="board">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${paramMap.pageIndex}">
                <input type="hidden" name="id" id="id" value="">
                <div class="input-group">
                    <select name="searchType" id="searchType" class="custom-select col-md-3">
                        <option selected value="">SEARCH SELECT</option>
                        <option <c:if test="${paramMap.searchType eq 'all'}">selected</c:if> value="all">ALL</option>
                        <option <c:if test="${paramMap.searchType eq 'title'}">selected</c:if> value="title">TITLE</option>
                        <option <c:if test="${paramMap.searchType eq 'content'}">selected</c:if> value="content">CONETEN</option>
                        <option <c:if test="${paramMap.searchType eq 'witer'}">selected</c:if> value="witer">WRITER</option>
                    </select> &nbsp;&nbsp;
                    <input type="search" name="searchTxt" value="${paramMap.searchTxt}" id="searchTxt" onkeydown="if(event.keyCode== 13) fnSearch();" class="form-control rounded col-md-9" placeholder="Search" aria-label="Search" aria-describedby="search-addon" />
                    &nbsp;&nbsp; <button type="button" class="btn btn-outline-primary">search</button>
                </div>
            </form>
            <br>

            <h6 style="float: right; font-weight: bold;">Total number of posts ${pageUtil.totalCount}</h6>
            <table class="table table-hover">
                <thead>
                <tr class="text-center">
                    <th scope="col" style="width: 10%">No</th>
                    <th scope="col">Title</th>
                    <th scope="col" style="width: 15%">Writer</th>
                    <th scope="col" style="width: 20%">Create Date</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="no" value="${pageUtil.reverseNumber}"/>
                    <c:choose>
                        <c:when test="${fn:length(resultList) > 0}">
                            <c:forEach var="result" items="${resultList}" varStatus="st">
                                <tr class="text-center">
                                    <td>${no}</td>
                                    <td><a href="javascript:void(0)" onclick="fnView('${result.ID}');">${result.TITLE}</a></td>
                                    <td>${result.USERNAME}</td>
                                    <td>${result.CREATED_AT}</td>
                                </tr>
                                <c:set var="no" value="${no-1}"/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr class="text-center">
                                <td colspan="4" style="font-weight: bold">NO DATA</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
            <%@ include file="/WEB-INF/jsp/cmmn/paging.jsp"%>
        </div>
        <%-- container mt-3--%>
        <button type="button" onclick="fnWriter();" id="fnWrite" style="float: right;" class="btn btn-custom">Write</button>

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

    function fnSearch(){
        $('#pageIndex').val(1);
        $('#board').attr('action','/sample/boardList.do').submit();
    }

    function fnView(id){
        $('#id').val(id);
        $('#board').attr('action','/sample/boardRead.do').submit();
    }

    function fnWriter(){
        $('#board').attr('action','/sample/boardWrite.do').submit();

    }

</script>