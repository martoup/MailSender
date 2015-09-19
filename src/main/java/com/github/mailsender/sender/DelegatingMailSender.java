package com.github.mailsender.sender;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelegatingMailSender {

	private final List<MailSender> delegates;

	@Autowired
	public DelegatingMailSender(List<MailSender> delegates) {
		this.delegates = delegates;
	}

	public Response sendMail(MailRequest request) {
		Response response = Response.DEFAULT_UNSUCCESSFUL_RESPONSE;
		for (MailSender delegate : delegates) {
			response = delegate.send(request);
			if (response.isSuccess())
				break;
		}
		return response;
	}

}
