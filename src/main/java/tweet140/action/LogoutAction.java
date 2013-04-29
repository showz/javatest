package tweet140.action;

import org.seasar.framework.aop.annotation.RemoveSession;
import org.seasar.struts.annotation.Execute;

public class LogoutAction {
	@Execute(validator=false)
	@RemoveSession(name="userDto")
	public String logout() {
		return "/?redirect=true";
	}
}
