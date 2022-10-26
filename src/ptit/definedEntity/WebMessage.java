package ptit.definedEntity;

public class WebMessage {
	private String messageType;
	private String message;
	
	public WebMessage() {
		super();
	}

	public WebMessage(String messageType, String message) {
		super();
		this.messageType = messageType;
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Boolean isFailed() {
		return this.messageType.equals("Thất bại");
	}
	
	public Boolean isSuccess() {
		return this.messageType.equals("Thành công");
	}
	
	public Boolean isNotSet() {
		return this.getMessageType().equals("") || this.getMessage().equals("");
	}
}
