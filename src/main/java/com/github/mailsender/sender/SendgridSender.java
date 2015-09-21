package com.github.mailsender.sender;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mailsender.sender.model.MailRequest;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;

@Component
class SendgridSender implements MailSender{

	private static final Logger LOG = Logger.getLogger(SendgridSender.class);

	private final SendGrid sendgridPusher;

	@Autowired
	public SendgridSender(SendGrid sendgridPusher) {
		this.sendgridPusher = sendgridPusher;
	}

	@Override
	public boolean send(MailRequest request) {

		Email email = makeEmail(request);
		try {
			LOG.debug("SendGrid: sending " + request);
			SendGrid.Response response = sendgridPusher.send(email);
			LOG.debug("SendGrid: response " + response.getMessage());
			return response.getStatus();
		} catch (Exception e) {
			LOG.error("SendGrid: error while sending.", e);
			return false;
		}
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
