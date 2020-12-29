<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%><%--shiro标签库--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>List Page</h1>
    Welcome: <shiro:principal></shiro:principal>
    <shiro:hasRole name="user">
    <br>
    <a href="${pageContext.request.contextPath}/views/user.jsp">user.jsp</a>
    </shiro:hasRole>
    <shiro:hasRole name="admin">
    <br>
    <a href="${pageContext.request.contextPath}/views/admin.jsp">admin.jsp</a>
    </shiro:hasRole>
    <br>
    <a href="${pageContext.request.contextPath}/shiro/logout">Logout</a>

    <br>
    <a href="${pageContext.request.contextPath}/shiro/testShiroAnnotation">testAdminShiroAnnotation</a>

</body>
</html>
