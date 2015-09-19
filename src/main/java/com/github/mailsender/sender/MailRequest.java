package com.github.mailsender.sender;

import java.util.Collections;
import java.util.List;

public class MailRequest {
	private String from;

	private String subject;

	private String content;

	private List<String> to = Collections.emptyList();

	private List<String> cc = Collections.emptyList();

	private List<String> bcc = Collections.emptyList();

	public String getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public List<String> getTo() {
		return to;
	}

	public List<String> getCc() {
		return cc;
	}

	public List<String> getBcc() {
		return bcc;
	}
}
