var Tweet = {
	register: function(tweet){
		var defer = $.Deferred();
		$.ajax({
			url: "/tweet140/tweetApi/register/",
			type: "POST",
			data: {'tweet': tweet},
			dataType: 'jsonp',
			success: defer.resolve,
			error: defer.reject
		});
		return defer.promise();
	},
	search: function(){
		var defer = $.Deferred();
		var currentPage = $("#currentPage").val()+1;
		$.ajax({
			url: "/tweet140/tweetApi/",
			type: "POST",
			data: {"currentPage": currentPage},
			dataType: 'jsonp',
			success: defer.resolve,
			error: defer.reject
		})
		return defer.promise();
	}
};

var Comment = {
	register: function(tweet_id, comment) {
		var defer = $.Deferred();
		$.ajax({
			url: "/tweet140/commentApi/register/",
			type: "POST",
			data: {'tweetId': tweet_id, 'comment': comment},
			dataType: 'jsonp',
			success: defer.resolve,
			error: defer.reject
		});
		return defer.promise();
	}
};

var makeTweetHtml = function(data) {
	var html = "";
	$.each(data.results, function(i, result) {
		var d = new Date(result.createdAt);
		var year  = d.getFullYear();
		var month = d.getMonth() + 1;
		var day   = d.getDate();
		var hour  = (d.getHours()   < 10) ? '0'+d.getHours()   : d.getHours();
		var min   = (d.getMinutes() < 10) ? '0'+d.getMinutes() : d.getMinutes();
		var sec   = (d.getSeconds() < 10) ? '0'+d.getSeconds() : d.getSeconds();
		
		html += '<hr/>';
		html += '<div class="tweet" id="'+result.id+'">';
		html += '<h3>'+result.tweet+'</h3>';
		html += '<p>'+year+'/'+month+'/'+day+' '+hour+':'+min+':'+sec+'</p>'
		if(result.comment) {
			html += '<ul>';
			$.each(result.comment, function(j, comm) {
				html += '<hr/>';
				html += '<li>'+comm.comment+'</li>';
			});
			html += '</ul>';
		}
		if(result.isComment) {
			html += '<div id="comment-form-'+result.id+'">';
			html += '<a id="'+result.id+'" class="comment">コメントする</a>';
			html += '</div>';
		}
		html += '</div>'
	});
	if(data.isNext) {
		html += '<input type="button" class="btn" id="next-read" name="next-read" value="更に読み込む"/>';
	}
	$("#archive").append(html);
};

$(function(){
	$("#tweet-button").on("click", function(){
		$("#currentPage").val(0);
		var tweet = $("#tweet-textarea").val();
		if(!tweet){
			alert('つぶやきを入力してください');
			return null;
		}
		Tweet.register(tweet).then(
			function(){
				Tweet.search().then(
						function(data){
							$("#archive").empty();
							$("#currentPage").val(parseInt($("#currentPage").val())+1);
							makeTweetHtml(data);
						},
						function(e) {
							alert('投稿取得に失敗しました');
						}
					);
			},
			function(e){
				alert('投稿に失敗しました');
			}
		);
	});
	
	$(document).on("click", "#next-read", function() {
		$("input#next-read").remove();
		Tweet.search().then(
			function(data){
				$("#currentPage").val(parseInt($("#currentPage").val())+1);
				makeTweetHtml(data);
			},
			function(e) {
				alert('投稿取得に失敗しました');
			}
		);
	});
	
	$(document).on("click", ".comment", function(){
		var id = $(this).attr("id");
		var html = "";
		this.remove();
		html += '<textarea id="comment-textarea-'+id+'" class="span8" name="comment"></textarea><br/>';
		html += '<button type="button" class="regist-comment btn btn-primary" id="comment-button-'+id+'">コメントする</button>';
		$("#comment-form-"+id).append(html);
	});
	
	$(document).on("click", ".regist-comment", function() {
		var id = $(this).attr("id");
		var comment = "";
		id = id.replace(/comment-button-/g, "");
		comment = $("#comment-textarea-"+id).val();
		if(!comment) {
			alert("コメントを入力してください。");
			return;
		}
		
		Comment.register(id, comment).then(
			function() {
				$("#currentPage").val(0);
				Tweet.search().then(
					function(data){
						$("#archive").empty();
						$("#currentPage").val(parseInt($("#currentPage").val())+1);
						makeTweetHtml(data);
					},
					function(e) {
						alert('投稿取得に失敗しました');
					}
				);
			},
			function(e) {
				alert('投稿取得に失敗しました');
			}
		);
	});
	
	Tweet.search().then(
		function(data){
			$("#currentPage").val(parseInt($("#currentPage").val())+1);
			makeTweetHtml(data);
		},
		function(e) {
			alert('投稿取得に失敗しました');
		}
	);
});