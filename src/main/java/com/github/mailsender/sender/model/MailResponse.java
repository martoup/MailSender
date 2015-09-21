package com.github.mailsender.sender.model;

import org.springframework.http.HttpStatus;

public class MailResponse {
	public static final MailResponse SUCCESSFUL = new MailResponse(true, HttpStatus.OK, "Email successfully sent.");
	public static final MailResponse UNSUCCESSFUL = new MailResponse(false, HttpStatus.SERVICE_UNAVAILABLE,
			"Couldn't send email rigth now.");

	private final boolean success;
	private final String message;
	private final HttpStatus status;

	public MailResponse(boolean success, HttpStatus status, String msg) {
		this.success = success;
		this.message = msg;
		this.status = status;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", message=" + message + ", status=" + status + "]";
	}

}
