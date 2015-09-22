package com.github.mailsender.controller;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mailsender.sender.DelegatingMailSender;
import com.github.mailsender.sender.MailSender;
import com.github.mailsender.utils.RequestValidator;

@Configuration
class TestContextConfiguration {

	@Bean
	public MailSender mailSenderA() {
		return mock(MailSender.class);
	}
	
	@Bean
	public MailSender mailSenderB() {
		return mock(MailSender.class);
	}
	
	@Bean
	public DelegatingMailSender mailSender(List<MailSender> mailSenders)
	{
		return new DelegatingMailSender(mailSenders);
	}
	
	@Bean
	public RequestValidator requestValidator() {
		return new RequestValidator(10, 20);
	}
	
	@Bean
	public MailSenderController senderController(DelegatingMailSender mailSender, RequestValidator requestValidator)
	{
		return new MailSenderController(mailSender, requestValidator);
	}
	
}