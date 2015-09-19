package com.github.mailsender.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;

@Component
class SendgridSender implements MailSender {

	@Autowired
	private SendGrid sendGridSender;

	@Override
	public Response send(MailRequest request) {

		Email email = makeEmail(request);
		Response response = Response.DEFAULT_UNSUCCESSFUL_RESPONSE;
		try {
			SendGrid.Response sgResponse = sendGridSender.send(email);
			response = new Response(sgResponse.getCode(), sgResponse.getMessage());
		} catch (SendGridException e) {
			// TODO add logging...
		}
		return response;
	}

	private Email makeEmail(MailRequest request) {
		Email email = new SendGrid.Email();
		email.setFrom(request.getFrom());
		email.setSubject(request.getSubject());
		email.setHtml(request.getContent());
		for (String to : request.getTo())
			email.addTo(to);

		for (String cc : request.getCc())
			email.addCc(cc);

		for (String bcc : request.getBcc())
			email.addBcc(bcc);

		return email;
	}

}
