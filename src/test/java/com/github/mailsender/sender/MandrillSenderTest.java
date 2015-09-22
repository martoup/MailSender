package com.github.mailsender.sender;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mailsender.RequestFactory;
import com.github.mailsender.sender.model.MailRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MandrillSenderTest {

	private static final String STATUS_ERROR = "\"status\" : \"error\"";

	private static final String STATUS_SENT = "\"status\" : \"sent\"";

	private WebResource mandrillPusher;

	private MandrillSender mandrillSender;

	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		mandrillPusher = mock(WebResource.class);
		objectMapper = mock(ObjectMapper.class);
	}

	@Test
	public void testSendShouldSuccess() throws JsonParseException, JsonMappingException, IOException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		mandrillSender = new MandrillSender(objectMapper, mandrillPusher, "");
		ClientResponse response = makeResponse(true);
		// when
		when(mandrillPusher.post(eq(ClientResponse.class), anyMap())).thenReturn(response);
		boolean res = mandrillSender.send(request);
		// then
		assertTrue(res);
	}

	@Test
	public void testSendShouldReturnFalseWhenSendingFails()
			throws JsonParseException, JsonMappingException, IOException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		mandrillSender = new MandrillSender(objectMapper, mandrillPusher, "");
		ClientResponse response = makeResponse(false);
		// when
		when(mandrillPusher.post(eq(ClientResponse.class), anyMap())).thenReturn(response);
		boolean res = mandrillSender.send(request);
		// then
		assertFalse(res);
	}

	@Test
	public void testSendShouldReturnFalseOnException() throws JsonParseException, JsonMappingException, IOException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		mandrillSender = new MandrillSender(objectMapper, mandrillPusher, "");
		// when
		when(mandrillPusher.post(eq(ClientResponse.class), anyMap())).thenThrow(new RuntimeException());
		boolean res = mandrillSender.send(request);
		// then
		assertFalse(res);
	}

	private ClientResponse makeResponse(boolean isSuccessfull) {
		ClientResponse response = mock(ClientResponse.class);
		when(response.getStatus()).thenReturn(200);
		when(response.getEntity(String.class)).thenReturn(isSuccessfull ? STATUS_SENT : STATUS_ERROR);
		return response;
	}
}
