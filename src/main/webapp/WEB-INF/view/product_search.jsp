<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Product List - Search</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/product_list.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/product_detail.css"/>"/>
    <script src="<c:url value="/js/jquery-2.2.4.js"/>"></script>
    <script src="<c:url value="/js/product_list.js"/>"></script>
</head>
<header>
    <div class="container">
        <a href="/">Info</a>
        <nav>
            <ul>
                <li><a href="<c:url value="/product_list"/>">Product List</a></li>
                <li><a href="<c:url value="/search"/>">Search</a></li>
            </ul>
        </nav>
    </div>
</header>
<body>
<div class="container">
<h1>Product List - Search</h1>
<br/><br/>
    <c:if test="${not empty products}">
    <table class="product_table">
        <tr>
            <th>Name</th>
            <th>Brand</th>
            <th>Price</th>
            <th>Quantity</th>
            <th></th>
        </tr>
        <c:forEach  items="${products}" var ="product">
            <tr type="element" id="${product.id}">
                <td><a href="/product_detail/${product.id}" type="product_name">${product.name}</a></td>
                <td>${product.brand}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td><img type="remove_button" src="<c:url value="/img/remove_button.png"/>"  element-id="${product.id}" alt="Remove product"></td>
            </tr>
        </c:forEach>
    </table>
    </c:if>
    <c:if test="${empty products}">
        <b>Product List is emprty.</b>
    </c:if>
    <a href="<c:url value="/add_product"/>" alt="Add new product">
        <img type="add_button" src="<c:url value="/img/add_button.png"/>" alt="Add new product">
    </a>
    <form name="update" method="post">
        <input type="text" name="name">
        <input type="submit" value="Search by name">
    </form>
    <form name="update" method="post">
        <input type="text" name="brand">
        <input type="submit" value="Search by brand">
    </form>
    <form name="update" method="post">
        <input type="hidden" name="leftLovers" value="y">
        <input type="submit" value="Show leftLovers (product which quantity is less than 5)" width="auto">
    </form>
</div>
</body>
</html>