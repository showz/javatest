<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/layout.jsp" flush="true">
<tiles:put name="content" type="string">
<meta charset="utf-8">
<h1>パスワードリセット</h1>
<html:errors />
<s:form>
  <label>パスワード</label>
  <html:text property="password" /><br/>
  <html:submit property="update" value="パスワードをリセットする" />
</s:form>
</tiles:put>
</tiles:insert>