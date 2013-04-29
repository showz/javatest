<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/layout.jsp" flush="true">
<tiles:put name="content" type="string">
<h1>ログイン</h1>
<html:errors />
<s:form>
  <label>メールアドレス</label>
  <html:text property="mail_address" /><br/>
  <label>パスワード</label>
  <html:password property="password" /><br/>
  <html:submit property="register" styleClass="btn btn-primary" value="ログイン or 新規登録" />
</s:form>
<s:link href="/resetPassword/">パスワードをリセットする</s:link>
</tiles:put>
</tiles:insert>