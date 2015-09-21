package com.github.mailsender.sender;

import com.github.mailsender.sender.model.MailRequest;

public interface MailSender {
	boolean send(MailRequest request);
}
