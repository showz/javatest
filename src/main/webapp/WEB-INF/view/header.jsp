<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<ul class="nav nav-pills pull-right">
  <li class="${activeTop}"><s:link href="/">トップ</s:link></li>
  <c:choose>
    <c:when test="${userDto != null && userDto.isLogin == true}">
      <li><s:link href="logout/">ログアウト</s:link></li>
    </c:when>
    <c:otherwise>
      <li class="${activeLogin}"><s:link href="login/">ログイン</s:link></li>
    </c:otherwise>
  </c:choose>
</ul>
<h3 class="muted">Tweet140sec</h3>