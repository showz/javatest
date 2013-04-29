package tweet140.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.log.Logger;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ResponseUtil;

import tweet140.dbflute.exbhv.CommentBhv;
import tweet140.dbflute.exbhv.TweetBhv;
import tweet140.dbflute.exentity.Comment;
import tweet140.dbflute.exentity.Tweet;
import tweet140.dto.UserDto;
import tweet140.form.CommentApiForm;

public class CommentApiAction {
	@Binding(bindingType = BindingType.MUST)
	public UserDto userDto;
	
	@ActionForm
	@Binding(bindingType = BindingType.MUST)
	public CommentApiForm commentApiForm;
	
	@Binding(bindingType = BindingType.MUST)
	public CommentBhv commentBhv;
	
	@Binding(bindingType = BindingType.MUST)
	public TweetBhv tweetBhv;
	
	@Binding(bindingType = BindingType.MUST)
	private HttpServletResponse response;

	@Execute(validator=false)
	public String register() {
		// login check
		if(userDto == null || userDto.isLogin != true || userDto.userId == 0) {
			try {
				ResponseUtil.write(response(503, "error"), "application/json");
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return null;
		}
		
		// get now timestamp
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		
		// set DB column value
		Comment comment = new Comment();
		comment.setUid(userDto.userId);
		comment.setIsPub("YES");
		comment.setTid(commentApiForm.tweetId);
		comment.setComment(commentApiForm.comment);
		comment.setCreatedAt(createdAt);
		comment.setUpdatedAt(createdAt);
		// insert comment table
		commentBhv.insert(comment);
		
		// count up tweet.comment_num
		Tweet tweet = tweetBhv.selectByPKValue(commentApiForm.tweetId);
		Logger logger = Logger.getLogger(CommentApiAction.class);
		logger.info(commentApiForm.tweetId);
		tweet.setCommentNum(tweet.getCommentNum()+1);
		tweetBhv.update(tweet);
		
		// response 
		try {
			ResponseUtil.write(response(200, "success"), "application/json");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
	
	private String response(int code, String msg) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		
		try {
			return responseJson(map);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
		}
		return "";
	}
	
	private String responseJson(Map<String, Object> map) throws Exception {
		if(!map.containsKey("code")) {
			throw new Exception("codeが指定されていません");
		}
		
		return String.format("%s(%s);", commentApiForm.callback, JSON.encode(map));
	}
}
