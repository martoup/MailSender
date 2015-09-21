package com.github.mailsender.sender.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MandrillMessage {

	@JsonProperty("html")
	private final String content;

	@JsonProperty("subject")
	private final String subject;

	@JsonProperty("from_email")
	private final String fromEmail;

	@JsonProperty("to")
	private final List<Recipient> to;

	public MandrillMessage(String content, String subject, String fromEmail, List<Recipient> to) {
		this.content = content;
		this.subject = subject;
		this.fromEmail = fromEmail;
		this.to = to;
	}

	public static class Recipient {
		@JsonProperty("email")
		private final String email;
		@JsonProperty("type")
		private final String type;

		public Recipient(String type, String email) {
			this.email = email;
			this.type = type;
		}
	}
}
