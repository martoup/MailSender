package com.github.mailsender.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Component
class MailgunSender implements MailSender {

	@Autowired
	private WebResource mailGunSender;

	@Override
	public Response send(MailRequest request) {
		MultivaluedMapImpl data = makeData(request);
		Response response = Response.DEFAULT_UNSUCCESSFUL_RESPONSE;
		try {
			ClientResponse clientResponse = mailGunSender.post(ClientResponse.class, data);
			String output = clientResponse.getEntity(String.class);
			response = new Response(clientResponse.getStatus(), output);
		} catch (UniformInterfaceException | ClientHandlerException e) {
			// TODO add logging...
		}

		return response;
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
