package tweet140.form;

import org.seasar.struts.annotation.EmailType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Minlength;
import org.seasar.struts.annotation.Required;

public class LoginForm {
	@Required
	@EmailType
	public String mail_address;
	
	@Required
	@Minlength(minlength=6)
	@Maxlength(maxlength=12)
	public String password;
}
