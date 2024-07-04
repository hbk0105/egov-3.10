<%--
	Description :

	Modification Information
	수정일		수정자			수정내용
	-------		-----------------------------------
	2021.04.08 	SANGS			최초작성

--%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Content-Script-Type" content="text/javascript"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge;"/>

<body>
<script src="/js/jquery-3.7.1.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        $('body').hide();
        if ('<c:out value="${msg}"/>' != '') {
            alert((('<c:out value="${msg}"/>').replace('<br/>', '\n')).replace('&lt;br&gt;', '\n'));
        }

        if ('<c:out value="${returnUrl}" escapeXml='false' />' != '') {
            $('#frm').attr('action', '<c:url value="${returnUrl}"/>');
            $('#frm').submit();
        }

    });


</script>

<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>

<form name="frm" id="frm" method="post" action="<c:url value="${returnUrl}"/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <c:out value='${paramInputs}' escapeXml='false'/>
</form>
<!--// footer -->
</body>
</html>


