package com.github.mailsender.sender;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.github.mailsender.RequestFactory;
import com.github.mailsender.sender.model.MailRequest;
import com.sendgrid.SendGridException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MailgunSenderTest {

	private MailgunSender sender;
	private WebResource mailgunSender;

	@Before
	public void setUp() {
		mailgunSender = mock(WebResource.class);
		sender = new MailgunSender(mailgunSender);
	}

	@Test
	public void testSendShouldSuccess() throws SendGridException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		ClientResponse response = makeResponse(true);
		// when
		when(mailgunSender.post(eq(ClientResponse.class), anyMap())).thenReturn(response);
		boolean res = sender.send(request);
		// then
		assertTrue(res);
	}

	@Test
	public void testSendShouldReturnFalseWhenSendingFails() throws SendGridException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		ClientResponse response = makeResponse(false);
		// when
		when(mailgunSender.post(eq(ClientResponse.class), anyMap())).thenReturn(response);
		boolean res = sender.send(request);
		// then
		assertFalse(res);
	}

	@Test
	public void testSendShouldReturnFalseOnException() throws SendGridException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		// when
		when(mailgunSender.post(eq(ClientResponse.class), anyMap())).thenThrow(new RuntimeException());
		boolean res = sender.send(request);
		// then
		assertFalse(res);
	}

	private ClientResponse makeResponse(boolean isSuccessfull) {
		int code = isSuccessfull ? 200 : 400;
		ClientResponse response = mock(ClientResponse.class);
		when(response.getStatus()).thenReturn(code);
		when(response.getEntity(String.class)).thenReturn("test");
		return response;
	}

}
