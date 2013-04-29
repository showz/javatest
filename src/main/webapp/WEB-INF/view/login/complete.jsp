<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/layout.jsp" flush="true">
<tiles:put name="content" type="string">
<p>
  ログイン完了しました。
</p>
<s:link href="/">TOPに戻る</s:link>
</tiles:put>
</tiles:insert>