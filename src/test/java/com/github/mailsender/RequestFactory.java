package com.github.mailsender;

import java.util.Arrays;

import com.github.mailsender.sender.model.MailRequest;

public class RequestFactory 
{

	public static MailRequest makeRequest() {
		MailRequest request = new MailRequest();
		request.setFrom("test@email.com");
		request.setContent("test");
		request.setSubject("test");
		request.setTo(Arrays.asList("test@test.com"));
		request.setCc(Arrays.asList("test1@test.com"));
		request.setBcc(Arrays.asList("test2@test.com"));
		return request;
	}

}
