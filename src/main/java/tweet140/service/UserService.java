package tweet140.service;

import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

import tweet140.dbflute.cbean.UserCB;
import tweet140.dbflute.exbhv.UserBhv;
import tweet140.dbflute.exentity.User;

public class UserService {
	@Binding(bindingType = BindingType.MUST)
	private UserBhv userBhv;
	
	public boolean isRegisteredMailAddress(String mailAddress) {
		if(mailAddress == null) {
			return false;
		}
		
		UserCB userCB = new UserCB();
		userCB.query().setMailAddress_Equal(mailAddress);
		userCB.query().setIsActive_Equal("YES");
		User user = userBhv.selectEntity(userCB);
		
		if(user == null) {
			return false;
		} else {
			return true;
		}
	}
}
