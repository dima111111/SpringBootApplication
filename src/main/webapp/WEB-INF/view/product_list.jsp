<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Product List</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/table.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/product_list.css"/>"/>
    <script src="<c:url value="/js/jquery-2.2.4.js"/>"></script>
    <script src="<c:url value="/js/product_list.js"/>"></script>
</head>
<header>
    <div class="container">
        <a href="/">Info</a>
        <nav>
            <ul>
                <li><a href="/product_list">Product List</a></li>
                <li><a href="/search">Search</a></li>
            </ul>
        </nav>
    </div>
</header>
<body>
    <div class="container">
        <h1>Product List</h1>
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
        <b class="text_empty_list" <c:if test="${not empty products}">style="display: none;"</c:if>>Product List is empty.</b>

        <a href="/add_product" alt="Add new product">
            <img type="add_button" src="<c:url value="/img/add_button.png"/>" alt="Add new product">
        </a>
    </div>
</body>
</html>