<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-08
  Time: 오후 1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/common/js/jquery-3.7.1.js"></script>
</head>
<body>

    <script>

        const eventSource = new EventSource('http://localhost:8080/notifications/subscribe/1');
        eventSource.addEventListener('sse', event => {
            console.log(event);
        });

    </script>
</body>
</html>
