package tweet140.form;

import org.seasar.struts.annotation.EmailType;
import org.seasar.struts.annotation.Required;

public class ResetPasswordForm {
	@Required
	@EmailType
	public String mail_address;
}
