<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Document</title>
        </head>

        <body>
            <ul>
                <li><a href="/">홈</a></li>
                <li><a href="/product/addForm">상품 등록</a></li>
            </ul>
            <h1> 상품 목록 페이지 </h1>
            <hr />
            <table border="1">
                <tr>
                    <th>번호</th>
                    <th>상품명</th>
                    <th>가격</th>
                    <th>재고</th>
                    <th>등록일</th>
                </tr>
                <c:forEach items="${productList}" var="product">
                    <tr>
                        <td>${product.id}</td> <!--톰캣이 알아서 스트링을 해석함. -->
                        <td> <a href="/product/${product.id}">${product.name}</a></td>
                        <td>${product.price}</td>
                        <td>${product.qty}</td>
                        <td>${product.createdAt}</td>
                    </tr>
                </c:forEach>
                
            </table>
        </body>

        </html>