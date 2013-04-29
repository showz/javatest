package tweet140.s2mai;

import tweet140.dto.ResetPasswordMailDto;

public interface ResetPasswordMailMai {
	public void sendMail(ResetPasswordMailDto dto);
}
