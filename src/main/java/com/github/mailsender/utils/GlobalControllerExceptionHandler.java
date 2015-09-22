package com.github.mailsender.utils;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.mailsender.sender.model.MailResponse;

/**
 * Class providing global exception handling logic. If an exception is thrown
 * <i>GlobalControllerExceptionHandler</i> catches the exception and returns
 * appropriate message to the user.
 * 
 * @author Martin Nikolov
 *
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {

	private static final Logger LOG = Logger.getLogger(GlobalControllerExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MailResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		LOG.error("Bad request. ", e);
		MailResponse response = new MailResponse(false, HttpStatus.BAD_REQUEST, e.getMessage());
		return new ResponseEntity<MailResponse>(response, response.getStatus());
	}

	@ExceptionHandler(HttpMessageConversionException.class)
	public ResponseEntity<MailResponse> handleHttpMessageConversionException(HttpMessageConversionException e) {
		LOG.error("Bad request. ", e);
		MailResponse response = new MailResponse(false, HttpStatus.BAD_REQUEST, "Invalid json input.");
		return new ResponseEntity<MailResponse>(response, response.getStatus());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<MailResponse> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		LOG.error("Bad request. ", e);
		MailResponse response = new MailResponse(false, HttpStatus.BAD_REQUEST, "Service supports only POST requests.");
		return new ResponseEntity<MailResponse>(response, response.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MailResponse> handleAll(Exception e) {
		LOG.error("Server error. ", e);
		MailResponse response = new MailResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, "Server error.");
		return new ResponseEntity<MailResponse>(response, response.getStatus());
	}
}