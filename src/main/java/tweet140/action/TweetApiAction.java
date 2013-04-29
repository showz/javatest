package tweet140.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import org.apache.log4j.Logger;
import org.seasar.dbflute.cbean.ListResultBean;
import org.seasar.dbflute.cbean.PagingResultBean;
import org.seasar.dbflute.cbean.grouping.GroupingOption;
import org.seasar.dbflute.cbean.grouping.GroupingRowEndDeterminer;
import org.seasar.dbflute.cbean.grouping.GroupingRowResource;
import org.seasar.dbflute.cbean.grouping.GroupingRowSetupper;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ResponseUtil;

import tweet140.dbflute.cbean.CommentCB;
import tweet140.dbflute.cbean.TweetCB;
import tweet140.dbflute.exbhv.CommentBhv;
import tweet140.dbflute.exbhv.TweetBhv;
import tweet140.dbflute.exentity.Comment;
import tweet140.dbflute.exentity.Tweet;
import tweet140.dto.CommentDto;
import tweet140.dto.TweetDto;
import tweet140.dto.UserDto;
import tweet140.form.TweetApiForm;

public class TweetApiAction {
	@Binding(bindingType = BindingType.MUST)
	public UserDto userDto;
	
	@ActionForm
	@Binding(bindingType = BindingType.MUST)
	public TweetApiForm tweetApiForm;
	
	@Binding(bindingType = BindingType.MUST)
	public TweetBhv tweetBhv;
	
	@Binding(bindingType = BindingType.MUST)
	public CommentBhv commentBhv;
	
	@Binding(bindingType = BindingType.MUST)
	private HttpServletResponse response;

	@Execute(validator=false)
	public String index() {
		TweetCB tweetCB = new TweetCB();
		tweetCB.query().setIsPub_Equal("YES");
		tweetCB.query().addOrderBy_UpdatedAt_Desc();
		tweetCB.paging(20, tweetApiForm.currentPage);
		PagingResultBean<Tweet> tweetResults= tweetBhv.selectPage(tweetCB);
		
		List<Tweet> list = tweetResults.getSelectedList();
		ArrayList<Integer> tidList = new ArrayList<Integer>();
		for(Tweet t : list) {
			tidList.add(t.getId());
		}
		CommentCB commentCB = new CommentCB();
		commentCB.query().setIsPub_Equal("YES");
		commentCB.query().setTid_InScope(tidList);
		commentCB.query().addOrderBy_Tid_Asc();
		ListResultBean<Comment> commentResults = commentBhv.selectList(commentCB);
		
		// response 
		try {
			ResponseUtil.write(response(200, "success", tweetResults, commentResults), "application/json");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
		}
		return null;
	}

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
		Tweet tweet = new Tweet();
		
		// set DB column value
		tweet.setUid(userDto.userId);
		tweet.setTweet(tweetApiForm.tweet);
		tweet.setIsPub("YES");
		tweet.setCommentNum(0);
		tweet.setCreatedAt(createdAt);
		tweet.setUpdatedAt(createdAt);
		// insert tweet table
		tweetBhv.insert(tweet);
		
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
	
	private String response(int code, String msg, PagingResultBean<Tweet> prb, ListResultBean<Comment> list) throws Exception {
		// make comment list
		HashMap<Integer, List<CommentDto>> commentMap = new HashMap<Integer, List<CommentDto>>();
		// if list exists
		if(list != null) {
			// grouping by tid
			GroupingOption<Comment> groupingOption = new GroupingOption<Comment>();
			groupingOption.setGroupingRowEndDeterminer(new GroupingRowEndDeterminer<Comment>() {
				public boolean determine(GroupingRowResource<Comment> rowResource, Comment nextEntity) {
					Comment currentEntity = rowResource.getCurrentEntity();
					Integer currentInitTid = currentEntity.getTid();
					Integer nextInitTid = nextEntity.getTid();
					return !currentInitTid.equals(nextInitTid);
				}
			});
			List<List<Comment>> groupingList = list.groupingList(new GroupingRowSetupper<List<Comment>, Comment>() {
			    public List<Comment> setup(GroupingRowResource<Comment> groupingRowResource) {
			        return new ArrayList<Comment>(groupingRowResource.getGroupingRowList());
			    }
			}, groupingOption);
			
			// make grouping commentDto list
			for(List<Comment> groupList : groupingList) {
				int tid = groupList.get(0).getTid();
				ArrayList<CommentDto> dtoList = new ArrayList<CommentDto>();
				for(Comment comment : groupList) {
					CommentDto commentDto = new CommentDto();
					commentDto.setId(comment.getId());
					commentDto.setTid(comment.getTid());
					commentDto.setUid(comment.getUid());
					commentDto.setComment(comment.getComment());
					commentDto.setCreatedAt(comment.getCreatedAt());
					
					dtoList.add(commentDto);
				}
				commentMap.put(tid, dtoList);
			}
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		ArrayList<TweetDto> results = new ArrayList<TweetDto>();
		for(Tweet result : prb.getSelectedList()) {
			TweetDto tweetDto = new TweetDto();
			tweetDto.setId(result.getId());
			tweetDto.setUid(result.getUid());
			tweetDto.setTweet(result.getTweet());
			tweetDto.setCreatedAt(result.getCreatedAt());
			if(userDto != null && userDto.isLogin && userDto.userId != result.getUid()) {
				tweetDto.setCommentOk(true);
			} else {
				tweetDto.setCommentOk(false);
			}
			
			//Logger logger = Logger.getLogger(TweetApiAction.class);
			//logger.info(result.getId());
			if(!commentMap.isEmpty()
			&& commentMap.containsKey(result.getId())
			&& !commentMap.get(result.getId()).isEmpty()) {
				tweetDto.setComment(commentMap.get(result.getId()));
			}
			
			results.add(tweetDto);
		}
		map.put("results", results);
		map.put("isNext", prb.isExistNextPage());
		
			
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
		
		return String.format("%s(%s);", tweetApiForm.callback, JSON.encode(map));
	}
}
