package com.github.mailsender.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.mailsender.sender.DelegatingMailSender;
import com.github.mailsender.sender.model.MailRequest;
import com.github.mailsender.sender.model.MailResponse;
import com.github.mailsender.utils.RequestValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Simple REST Controller providing email sending functionality.
 * It supports JSON requests which are converted to <i>MailRequest</i>
 * and produces <i>MailResponses</i> which are converted to JSON. 
 * 
 * @author Martin Nikolov
 * @see MailRequest
 * @see MailResponse
 */
@RestController
@Api(produces = "application/json", consumes = "application/json")
public class MailSenderController {

	private static final Logger LOG = Logger.getLogger(MailSenderController.class);

	private final DelegatingMailSender sender;

	private final RequestValidator validator;

	@Autowired
	public MailSenderController(DelegatingMailSender sender, RequestValidator validator) {
		this.sender = sender;
		this.validator = validator;
	}

	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "Invalid JSON input."),
			@ApiResponse(code = 503, message = "No email service provider is available.") })
	@RequestMapping(value = "/mail", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MailResponse> sendMail(@RequestBody MailRequest request) {
		LOG.debug("Handling request: " + request);
		validator.validateRequest(request);
		MailResponse response = sender.sendMail(request);
		LOG.debug("Response: " + response);
		return new ResponseEntity<MailResponse>(response, response.getStatus());
	}
}
