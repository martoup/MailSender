package com.github.mailsender.sender.model;

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

	public void setFrom(String from) {
		this.from = from;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	@Override
	public String toString() {
		return "MailRequest [from=" + from + ", subject=" + subject + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc
				+ "]";
	}
}
