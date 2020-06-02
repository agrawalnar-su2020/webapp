<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="/HomeStyle.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<a class = "btn btn-primary" href="/home" >Home</a>
<br>
<br>
<c:choose>
    <c:when test="${empty cartItem}">
        <h3>Cart is empty</h3>
    </c:when>
    <c:otherwise>
        <h3>Items in cart</h3>
        <br>
        <br>
        <table class="table-condensed" border="1">
                <tr>
                    <td id="td1"> Title </td>
                    <td id="td1"> ISBN </td>
                    <td id="td1"> Author </td>
                    <td id="td1"> Publication Date</td>
                    <td id="td1"> Price </td>
                    <td id="td1"> Selected Quantity</td>
                </tr>

                <c:forEach items="${cartItem}" var="item">
                    <tr>
                        <td> ${item.book.title} </td>
                        <td> ${item.book.ISBN} </td>
                        <td> ${item.book.authors} </td>
                        <td> ${item.book.publicationDate} </td>
                        <td> ${item.book.price} </td>
                        <td> ${item.quantityAdd} </td>
                    </tr>

                </c:forEach>
            </table>
    </c:otherwise>
</c:choose>

</body>
</html>