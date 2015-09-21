package com.github.mailsender.sender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mailsender.sender.model.MailRequest;
import com.github.mailsender.sender.model.MailResponse;

@Component
public class DelegatingMailSender {

	private final List<MailSender> delegates;

	@Autowired
	public DelegatingMailSender(List<MailSender> delegates) {
		this.delegates = delegates;
	}

	public MailResponse sendMail(MailRequest request) {
		boolean isSuccessful = false;
		for (MailSender delegate : delegates) {
			isSuccessful = delegate.send(request);
			if (isSuccessful)
				break;
		}

		if (isSuccessful)
			return MailResponse.SUCCESSFUL;
		else
			return MailResponse.UNSUCCESSFUL;
	}

}
