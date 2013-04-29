<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>[Tweet140]</title>
<meta charset="utf-8">
<link rel="shortcut icon" href="/tweet140/img/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="/tweet140/css/bootstrap.css" />
<link rel="stylesheet" href="/tweet140/css/common.css" />
<div class="container-narrow">
  <div class="masthead">
    <tiles:insert page="header.jsp" flush="true" />
  </div>
  <hr>
  <div class="jumbotron">
    <tiles:insert attribute="content" flush="true" />
  </div>
  <hr>
  <div class="footer">
    <tiles:insert page="footer.jsp" flush="true" />
  </div>
</div>
<script src="/tweet140/js/jquery-1.9.1.min.js"></script>
<tiles:insert attribute="add_js" flush="true" />
