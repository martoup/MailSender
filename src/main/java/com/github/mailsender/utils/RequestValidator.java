package com.github.mailsender.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.mailsender.sender.model.MailRequest;

public class RequestValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private final int maxSubjectLength;

	private final int maxRecipients;

	private final Pattern pattern;

	public RequestValidator(int maxRecipients, int maxSubjectLength) {
		this.maxSubjectLength = maxSubjectLength;
		this.maxRecipients = maxRecipients;
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public void validateRequest(MailRequest request) {
		validateTo(request.getTo());
		validateNonRequieredEmailFileds(request.getCc());
		validateNonRequieredEmailFileds(request.getBcc());

		int totalRecipients = request.getTo().size() + request.getBcc().size() + request.getCc().size();
		validateRecipientsLimit(totalRecipients);

		validateFrom(request.getFrom());

		validateSubject(request.getSubject());
		validateContent(request.getContent());
	}

	void validateRecipientsLimit(int recipitens) {
		if (recipitens > maxRecipients)
			throw new IllegalArgumentException("Can not send to more than " + maxRecipients + " recipients.");
	}

	void validateTo(List<String> tos) {
		if (tos == null)
			throw new IllegalArgumentException("Missing 'to' field.");
		if (tos.size() == 0)
			throw new IllegalArgumentException("Field 'to' can not be empty.");
		for (String to : tos)
			validateEmailAddress(to);
	}

	void validateFrom(String from) {
		if (from == null)
			throw new IllegalArgumentException("Missing 'from' field.");
		if (from.length() == 0)
			throw new IllegalArgumentException("Field 'from' can not be empty.");
		validateEmailAddress(from);
	}

	void validateNonRequieredEmailFileds(List<String> mailAddresses) {
		if (mailAddresses == null)
			throw new IllegalArgumentException("Invalid recipients field.");
		for (String address : mailAddresses)
			validateEmailAddress(address);
	}

	void validateEmailAddress(String address) {
		Matcher matcher = pattern.matcher(address);
		if (!matcher.matches())
			throw new IllegalArgumentException("Invalid Email: " + address);
	}

	void validateSubject(String subject) {
		if (subject == null)
			throw new IllegalArgumentException("Missing 'subject' field.");
		if (subject.length() == 0)
			throw new IllegalArgumentException("Field 'subject' can not be empty.");
		if (subject.length() > maxSubjectLength)
			throw new IllegalArgumentException(
					"The subject can not be longer than " + maxSubjectLength + " characters.");
	}

	void validateContent(String content) {
		if (content == null)
			throw new IllegalArgumentException("Missing `content` field.");
		if (content.length() == 0)
			throw new IllegalArgumentException("Field `content` can not be empty.");
	}
}
