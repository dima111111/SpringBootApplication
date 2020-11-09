<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Product <c:out value="${product.name}"></c:out></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css"/>"/>
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
        <c:if test="${not empty product}">
            <h1>Product <c:out value="${product.name}"></c:out></h1>
            <br/><br/>
            <form name="update" method="post">
                <table class="product_table">
                    <tr>
                        <th>Property</th>
                        <th>Value</th>
                    </tr>
                    <tr>
                        <th>Name</th>
                        <th><input type="text" name="name" value="${product.name}"></th>
                    </tr>
                    <tr>
                        <th>Brand</th>
                        <th><input type="text" name="brand" value="${product.brand}"></th>
                    </tr>
                    <tr>
                        <th>Price</th>
                        <th><input type="text" name="price" value="${product.price}"></th>
                    </tr>
                    <tr>
                        <th>Quantity</th>
                        <th><input type="text" name="quantity" value="${product.quantity}"></th>
                    </tr>
                </table>
                <input type="submit" value="Save">
            </form>
        </c:if>
        <c:if test="${empty product}">
            <b>Current product not found.</b>
        </c:if>
    </div>
</body>
</html>