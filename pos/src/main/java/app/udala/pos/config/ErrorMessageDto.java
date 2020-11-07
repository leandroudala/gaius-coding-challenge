package app.udala.pos.config;

public class ErrorMessageDto {
	private int status;
	private String message;

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public ErrorMessageDto(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
