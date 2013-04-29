package tweet140.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import tweet140.dto.ResetPasswordMailDto;
import tweet140.dto.UserDto;
import tweet140.form.ResetPasswordForm;
import tweet140.s2mai.ResetPasswordMailMai;
import tweet140.service.UserService;

public class ResetPasswordAction {
	
	@Binding(bindingType = BindingType.MUST)
	public UserService userService;
	
	@Binding(bindingType = BindingType.MUST)
	public UserDto userDto;
	
	@Binding(bindingType = BindingType.MUST)
	private HttpServletRequest request;		
	
	public String activeLogin = "active";
	
	@ActionForm
	public ResetPasswordForm resetPasswordForm;
	
	@Execute(validator=false)
	public String index() {
		return "index.jsp";
	}
	
	@Execute(validator=false)
	public String sendResetMail() {
		// check regist mailAddress
		if(userService.isRegisteredMailAddress(resetPasswordForm.mail_address)) {
			// get Session
			HttpSession session = request.getSession(true);
			// regist mailAddress for session
			session.setAttribute("mail_address", resetPasswordForm.mail_address);
			// read mail dicon
			S2Container container = S2ContainerFactory.create("mailtest.dicon");
			// get mai object (mail access interface)
			ResetPasswordMailMai mai = (ResetPasswordMailMai) container.getComponent(ResetPasswordMailMai.class);
			// get mail Dto
			ResetPasswordMailDto dto = new ResetPasswordMailDto();
			// set mailAddress for reset mail
			dto.setName(resetPasswordForm.mail_address);
			// set mail To for reset mail
			dto.setTo(resetPasswordForm.mail_address);
			// set sessionId for reset mail
			dto.setSessionId(session.getId());
			// send mail
			mai.sendMail(dto);
		}
		
		return "sendResetMail.jsp";
	}
}
