<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>User Home</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<a class = "btn btn-primary" href="/updatedetail" >Update Detail</a>
<a class = "btn btn-danger" href="/logout" style="float:right"  >Logout</a>

<h2> Welcome ${user.firstName} </h2>
<br>
<br>
<h2>User Details</h2>
<br>
<br>
<table class="table">
    <tr>
        <td>First Name:</td>
        <td><input name="firstName" size="30" value="${user.firstName}" readonly />
        </td>
    </tr>

    <tr>
        <td>Last Name:</td>
        <td><input name="lastName" size="30" value="${user.lastName}" readonly />
        </td>
    </tr>

    <tr>
        <td>Email Id:</td>
        <td><input name="email" size="30" value="${user.email}" readonly />
        </td>
    </tr>

</table>

</body>
</html>