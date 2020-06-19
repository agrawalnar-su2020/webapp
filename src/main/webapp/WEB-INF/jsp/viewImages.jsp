<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Book Images</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
</head>
<body>
<a class = "btn btn-primary" href="${contextPath}/home" >Home</a>
<br>
<br>
<h2> Book Images</h2>
<br>
<br>
<c:forEach items="${imagesURL}" var="image">
    <img src="${image}"  width="300" height="200">
    <br>
    <br>
</c:forEach>
</body>
</html>