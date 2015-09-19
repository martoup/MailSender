package com.github.mailsender.sender;

public class Response {
	public static final Response DEFAULT_UNSUCCESSFUL_RESPONSE = new Response(500, "Server error");
	private final boolean success;
	private final String message;

	public Response(int code, String msg) {
		this.success = code == 200;
		this.message = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return this.message;
	}
}
