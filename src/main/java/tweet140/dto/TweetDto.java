package tweet140.dto;

import java.sql.Timestamp;
import java.util.List;

public class TweetDto {
	public int id;
	public int uid;
	public String tweet;
	public Timestamp createdAt;
	public boolean isCommentOk;
	public List<CommentDto> comment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isCommentOk() {
		return isCommentOk;
	}
	public void setCommentOk(boolean isCommentOk) {
		this.isCommentOk = isCommentOk;
	}
	public List<CommentDto> getComment() {
		return comment;
	}
	public void setComment(List<CommentDto> comment) {
		this.comment = comment;
	}

}
