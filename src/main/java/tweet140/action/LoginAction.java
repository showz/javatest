package tweet140.action;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ActionMessagesUtil;

import tweet140.dbflute.cbean.UserCB;
import tweet140.dbflute.exbhv.UserBhv;
import tweet140.dbflute.exentity.User;
import tweet140.dto.UserDto;
import tweet140.form.LoginForm;
import tweet140.service.UserService;

public class LoginAction {
	@Binding(bindingType = BindingType.MUST)
	public UserBhv userBhv;
	
	@Binding(bindingType = BindingType.MUST)
	public UserService userService;
	
	@Binding(bindingType = BindingType.MUST)
	public UserDto userDto;
	
	@Binding(bindingType = BindingType.MUST)
	private HttpServletRequest request;
	
	public String activeLogin = "active";
	
	@ActionForm
	@Binding(bindingType = BindingType.MUST)
	public LoginForm loginForm;
	
	@Execute(validator=false)
	public String index() {
		if(userDto != null && userDto.isLogin == true) {
			return "/?redirect=true";
		}
		return "index.jsp";
	}
	
	@Execute(validator=true, input="index.jsp")
	public String register() {
		String password_hash = toHash(loginForm.password);
		
		if(!userService.isRegisteredMailAddress(loginForm.mail_address)) {
			register(loginForm.mail_address, password_hash);
		}
		
		int userId = getUserId(loginForm.mail_address, password_hash);
		if(userId == 0) {
			return responseError("errors.login_error");
		} else {
			userDto.isLogin = true;
			userDto.userId  = userId;
		}
		return "complete?redirect=true";
	}
	
	@Execute(validator=false)
	public String complete() {
		return "complete.jsp";
	}
	
	private int getUserId(String mailAddress, String password) {
		if(mailAddress == null) {
			return 0;
		}
		if(password == null) {
			return 0;
		}
		
		UserCB userCB = new UserCB();
		userCB.query().setMailAddress_Equal(mailAddress);
		userCB.query().setPassword_Equal(password);
		userCB.query().setIsActive_Equal("YES");
		User user = userBhv.selectEntity(userCB);
		
		if(user != null) {
			return user.getId();
		} else {
			return 0;
		}
	}
	
	private boolean register(String mailAddress, String password) {
		if(mailAddress == null) {
			return false;
		}
		if(password == null) {
			return false;
		}
		
		Timestamp createdAt = new Timestamp(System.currentTimeMillis());
		User user = new User();
		user.setMailAddress(mailAddress);
		user.setPassword(password);
		user.setIsActive("YES");
		user.setCreatedAt(createdAt);
		
		userBhv.insert(user);
		return true;
	}
	
	private String responseError(String error_type) {
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error_type));
		ActionMessagesUtil.addMessages(request, messages);
		return "index.jsp";
	}
	
	private String toHash(String str) {
		final String salt = "8f0969794fac41ded7d45c5c2bc302ff";
		final String alg  = "SHA-256";
		String hash = null;
		
		String str_salt = str+salt;
		try {
			MessageDigest md = MessageDigest.getInstance(alg);
			hash = new BigInteger(1, md.digest(str_salt.getBytes())).toString(16);
		} catch(NoSuchAlgorithmException e) {
		}
		
		return hash;
	}
}
