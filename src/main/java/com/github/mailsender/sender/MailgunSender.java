package com.github.mailsender.sender;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.mailsender.sender.model.MailRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Component
class MailgunSender implements MailSender {

	private static final Logger LOG = Logger.getLogger(MailgunSender.class);

	private final WebResource mailgunPusher;

	@Autowired
	public MailgunSender(WebResource mailgunPusher) {
		this.mailgunPusher = mailgunPusher;
	}

	@Override
	public boolean send(MailRequest request) {
		MultivaluedMapImpl data = makeData(request);
		try {
			LOG.debug("Mailgun: sending " + request);
			ClientResponse clientResponse = mailgunPusher.post(ClientResponse.class, data);
			LOG.debug("Mailgun: response " + clientResponse.getEntity(String.class));
			return clientResponse.getStatus() == HttpStatus.OK.value();
		} catch (Exception e) {
			LOG.error("Mailgun: error while sending.", e);
			return false;
		}
	}

	private MultivaluedMapImpl makeData(MailRequest request) {
		MultivaluedMapImpl data = new MultivaluedMapImpl();

		data.add("from", request.getFrom());
		data.add("subject", request.getSubject());
		data.add("html", request.getContent());

		for (String to : request.getTo())
			data.add("to", to);

		for (String cc : request.getCc())
			data.add("cc", cc);

		for (String bcc : request.getBcc())
			data.add("bcc", bcc);

		return data;
	}

}
