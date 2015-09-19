package com.github.mailsender.sender;

public interface MailSender {
	Response send(MailRequest request);
}
