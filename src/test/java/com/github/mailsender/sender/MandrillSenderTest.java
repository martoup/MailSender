package com.github.mailsender.sender;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mailsender.RequestFactory;
import com.github.mailsender.sender.model.MailRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MandrillSenderTest {

	private WebResource mandrillPusher;

	private MandrillSender mandrillSender;

	@Before
	public void setUp() {
		mandrillPusher = mock(WebResource.class);
	}

	@Test
	public void testSendShouldSuccess() throws JsonParseException, JsonMappingException, IOException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		mandrillSender = new MandrillSender(makeMapper(true), mandrillPusher, "");
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
		mandrillSender = new MandrillSender(makeMapper(false), mandrillPusher, "");
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
		mandrillSender = new MandrillSender(makeMapper(true), mandrillPusher, "");
		// when
		when(mandrillPusher.post(eq(ClientResponse.class), anyMap())).thenThrow(new RuntimeException());
		boolean res = mandrillSender.send(request);
		// then
		assertFalse(res);
	}

	private ObjectMapper makeMapper(boolean isSuccessfull)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> mapToReturn = new HashMap<String, String>(1);
		mapToReturn.put("status", isSuccessfull ? "sent" : "error");
		ObjectMapper mapper = mock(ObjectMapper.class);
		when(mapper.readValue(anyString(), any(TypeReference.class))).thenReturn(mapToReturn);
		return mapper;
	}

	private ClientResponse makeResponse(boolean isSuccessfull) {
		int code = isSuccessfull ? 200 : 400;
		ClientResponse response = mock(ClientResponse.class);
		when(response.getStatus()).thenReturn(code);
		when(response.getEntity(String.class)).thenReturn("test");
		return response;
	}
}
