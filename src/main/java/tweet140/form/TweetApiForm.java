package tweet140.form;

import org.seasar.struts.annotation.IntegerType;
import org.seasar.struts.annotation.Required;

public class TweetApiForm {
	@Required
	public String tweet;
	
	@IntegerType
	public int tweetId;
	
	public String callback;
	public int limit;
	public int currentPage;

}
