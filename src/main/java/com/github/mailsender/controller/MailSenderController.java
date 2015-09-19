package com.github.mailsender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.mailsender.sender.DelegatingMailSender;
import com.github.mailsender.sender.MailRequest;
import com.github.mailsender.sender.Response;
import com.github.mailsender.utils.RequestValidator;

@RestController
public class MailSenderController {

	@Autowired
	private DelegatingMailSender sender;
	
	@Autowired
	private RequestValidator validator;
	
	@RequestMapping(value = "mail", method = RequestMethod.POST)
	public Response sendMail(@RequestBody MailRequest email) {
		validator.validateRequest(email);
		return sender.sendMail(email);
	}
}
