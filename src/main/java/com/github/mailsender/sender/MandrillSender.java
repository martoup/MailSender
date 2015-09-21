package com.github.mailsender.sender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mailsender.sender.model.MailRequest;
import com.github.mailsender.sender.model.MandrillMessage;
import com.github.mailsender.sender.model.MandrillMessage.Recipient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component
public class MandrillSender implements MailSender {

	private static final Logger LOG = Logger.getLogger(MandrillSender.class);

	private ObjectMapper objectMapper;

	private WebResource mandrillPusher;

	private String apiKey;

	@Autowired
	public MandrillSender(ObjectMapper objectMapper, WebResource mandrillPusher,
			@Value("${mandrill.apiKey}") String apiKey) {
		this.objectMapper = objectMapper;
		this.mandrillPusher = mandrillPusher;
		this.apiKey = apiKey;
	}

	@Override
	public boolean send(MailRequest request) {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("key", apiKey);
		dataMap.put("message", makeMessage(request));

		try {
			LOG.debug("Mandrill: sending " + request);
			String data = objectMapper.writeValueAsString(dataMap);
			ClientResponse clientResponse = mandrillPusher.post(ClientResponse.class, data);
			String response = clientResponse.getEntity(String.class);
			LOG.debug("Mandrill: response " + response);
			return (clientResponse.getStatus() == HttpStatus.OK.value()) && isMessageSent(response);
		} catch (Exception e) {
			LOG.error("Mandrill: error while sending. ", e);
			return false;
		}
	}

	private MandrillMessage makeMessage(MailRequest request) {
		List<Recipient> recipients = new ArrayList<Recipient>();

		for (String to : request.getTo())
			recipients.add(new Recipient("to", to));

		for (String cc : request.getCc())
			recipients.add(new Recipient("cc", cc));

		for (String bcc : request.getBcc())
			recipients.add(new Recipient("bcc", bcc));

		return new MandrillMessage(request.getContent(), request.getSubject(), request.getFrom(), recipients);
	}

	private boolean isMessageSent(String response) {
		try {
			Map<String, String> data = objectMapper.readValue(response, new TypeReference<HashMap<String, String>>() {
			});
			return "sent".equals(data.get("status"));
		} catch (Exception e) {
			LOG.error("Mandrill: error while deserializing: " + response, e);
			return false;
		}
	}
}
