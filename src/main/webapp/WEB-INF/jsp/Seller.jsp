<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Seller Home</title>
    <link rel="stylesheet" href="/HomeStyle.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
</head>
<body>
<a class = "btn btn-primary" href="${contextPath}/home" >Home</a>
<a class = "btn btn-primary" href="${contextPath}/seller/addbook" >Add Book</a>

<h2>Below are the your books available on book store</h2>
<br>
<br>

<table class="table-condensed" border="1">

    <tr>
        <td id="td1"> Title </td>
        <td id="td1"> ISBN </td>
        <td id="td1"> Author </td>
        <td id="td1"> Publication Date</td>
        <td id="td1"> Quantity </td>
        <td id="td1"> Price </td>
        <td id="td1"> Update Book </td>
        <td id="td1"> Delete Book </td>
    </tr>

    <c:forEach items="${sellerBooks}" var="book">
        <tr>
            <td> ${book.title} </td>
            <td> ${book.ISBN} </td>
            <td> ${book.authors} </td>
            <td> ${book.publicationDate} </td>
            <td> ${book.quantity} </td>
            <td> ${book.price} </td>
            <td align="center"><a class = "btn btn-success" href="${contextPath}/seller/updatebook?id=${book.bookID}" > Update </a></td>
            <td align="center"><a class = "btn btn-danger" href="${contextPath}/seller/deletebook?id=${book.bookID}" onclick = "if (! confirm('Book will be deleted permanently, want to continue?')) return false;" > Delete </a></td>
        </tr>

    </c:forEach>
</table>

</body>
</html>