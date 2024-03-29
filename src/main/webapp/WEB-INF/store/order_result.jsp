<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="/css/store/order_result.css">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic|Noto+Sans+KR&display=swap" rel="stylesheet">
<body>
<div id="order_result">
   	<div id="title">주문내역</div>
   	<c:if test="${ovo ne null}">
  	  <div class="list">
          <div class="list_panel">
              <div class="title">고객명</div>
              <div class="content">${ovo.order_customer }</div>
          </div>
          <div class="list_panel">
              <div class="title">주소</div>
              <div class="content">${ovo.order_address }</div>
          </div>
          <div class="list_panel">
              <div class="title">연락처</div>
              <div class="content">${ovo.order_contact }</div>
          </div>
          <div class="list_panel">
              <div class="title">주문물품</div>
              <div class="content">${prodTitle} x ${counts }개</div>
          </div>
          <div class="list_panel">
              <div class="title">배송메모</div>
              <div class="content">${ovo.order_comment }</div>
          </div>
     </div>
     </c:if>
     <c:if test="${ovo eq null}">
  	  <div class="list">
          <div class="list_panel">
              <div class="title">고객명</div>
              <div class="content">${ovo.order_customer }</div>
          </div>
          <div class="list_panel">
              <div class="title">주소</div>
              <div class="content">${ovo.order_address }</div>
          </div>
          <div class="list_panel">
              <div class="title">연락처</div>
              <div class="content">${ovo.order_contact }</div>
          </div>
          <div class="list_panel">
              <div class="title">주문물품</div>
              <div class="content">${prodTitle }</div>
          </div>
          <div class="list_panel">
              <div class="title">배송메모</div>
              <div class="content">${ovo.order_comment }</div>
          </div>
     </div>
     </c:if>
    <div id="end">주문해주셔서 감사합니다</div>
</div>
</body>
</html>