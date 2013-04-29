<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/layout.jsp" flush="true">
<tiles:put name="content" type="string">
<p>メールを送信しました。<br/>
※メールが来ない場合、メールアドレスが間違っているか登録していません。<br/>
<s:link href="/">トップに戻る</s:link>
</tiles:put>
</tiles:insert>