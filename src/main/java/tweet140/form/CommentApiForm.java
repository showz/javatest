package tweet140.form;

import org.seasar.struts.annotation.IntegerType;
import org.seasar.struts.annotation.Required;

public class CommentApiForm {
	@Required
	public String comment;
	
	@IntegerType
	public int tweetId;
	
	public String callback;
}
