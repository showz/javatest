<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/layout.jsp" flush="true">
<tiles:put name="content" type="string">
<h1>Top</h1>
<c:if test="${userDto != null && userDto.isLogin == true}" >
<div id="new-tweet" class="clearfix">
  <label for="textarea">あなたの思いをつぶやいてください。</label>
  <div class="input">
    <textarea id="tweet-textarea" class="span8" name="tweet"></textarea>
    <br/>
    <button id="tweet-button" class="btn btn-primary" type="button" data-loading-text="投稿中...">投稿する</button>
  </div>
</div>
</c:if>
<input type="hidden" name="currentPage" id="currentPage" value="0" />
<div id="archive">
</div>
</tiles:put>
<tiles:put name="add_js" type="string">
<script src="/tweet140/js/top.js"></script>
</tiles:put>
</tiles:insert>