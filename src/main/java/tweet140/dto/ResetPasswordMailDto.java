package tweet140.dto;

public class ResetPasswordMailDto {
	private String name;
	private String to;
	private String sessionId;
	private String resetPasswordUrl = "http://localhost:8080/tweet140/updatePassword/edit/";
	private String subject = "パスワードリセットメール";
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId= sessionId;
	}
	
	public String getResetPasswordUrl() {
		return resetPasswordUrl;
	}
	
	public String getSubject() {
		return subject;
	}

}
