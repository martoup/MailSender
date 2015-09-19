package com.github.mailsender.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.mailsender.sender.MailRequest;

@Component
public class RequestValidator {

	@Value("${maxSubjectLength}")
	private int maxSubjectLength;

	@Value("${maxRecipients}")
	private int maxRecipients;

	public void validateRequest(MailRequest request) {
		validateTo(request.getTo());
		validateNonRequieredEmailFileds(request.getCc());
		validateNonRequieredEmailFileds(request.getBcc());
		
		int totalRecipients = request.getTo().size() + request.getBcc().size() + request.getCc().size();
		validateRecipientsLimit(totalRecipients);
		
		validateFrom(request.getFrom());

		vaildateContent(request.getContent());
		vaildateSubject(request.getSubject());
	}

	private void validateRecipientsLimit(int recipitens) {
		if (recipitens > maxRecipients)
			throw new IllegalArgumentException("Can not send to more than " + maxRecipients + " recipients.");
	}
	
	private void validateTo(List<String> tos)
	{
		if(tos == null)
			throw new IllegalArgumentException("Invalid To field.");
		if(tos.size() == 0)
			throw new IllegalArgumentException("To can not be empty.");
		for(String to : tos)
			validateEMailAddress(to);
	}
	
	private void validateFrom(String from)
	{
		if(from == null)
			throw new IllegalArgumentException("Invalid From field.");
		if(from.length() == 0)
			throw new IllegalArgumentException("From can not be empty.");
		validateEMailAddress(from);
	}
	
	private void validateNonRequieredEmailFileds(List<String> mailAddresses)
	{
		if(mailAddresses == null)
			throw new IllegalArgumentException("Invalid recipients field.");
		for(String address : mailAddresses)
			validateEMailAddress(address);
	}

	private void validateEMailAddress(String address) {
		// TODO Implement this
		
	}

	// TODO Export string to properties file:
	private void vaildateSubject(String subject) {
		if (subject == null)
			throw new IllegalArgumentException("Invalid subject.");
		if (subject.length() == 0)
			throw new IllegalArgumentException("Subject can not be empty.");
		if (subject.length() > maxSubjectLength)
			throw new IllegalArgumentException("Subject can not be longer than " + maxSubjectLength + " characters.");
	}

	private void vaildateContent(String content) {
		if (content == null)
			throw new IllegalArgumentException("Invalid content.");
		if (content.length() == 0)
			throw new IllegalArgumentException("Subject can not be empty.");
	}

}
