<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Login Page</h1>

    <form action="${pageContext.request.contextPath}/shiro/login" method="post">
        username: <input type="text" name="username" id="">
        password: <input type="password" name="password" id="">

        <input type="submit" value="Submit">
    </form>
</body>
</html>
