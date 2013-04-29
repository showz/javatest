package tweet140.action;

import org.seasar.struts.annotation.Execute;

public class UpdatePasswordAction {
	@Execute(validator=false, urlPattern="edit/{sessionId}")
	public String edit() {
		return "edit.jsp";
	}
	
	@Execute(validator=false)
	public String update() {
		return "complete?redirect=true";
	}
	
	@Execute(validator=false)
	public String complete() {
		return "complete.jsp";
	}
}
