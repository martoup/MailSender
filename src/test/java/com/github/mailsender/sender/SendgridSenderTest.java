package com.github.mailsender.sender;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.github.mailsender.RequestFactory;
import com.github.mailsender.sender.model.MailRequest;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

public class SendgridSenderTest {

	private SendgridSender sendgridSender;
	private SendGrid sendGrid;

	@Before
	public void setUp() {
		sendGrid = mock(SendGrid.class);
		sendgridSender = new SendgridSender(sendGrid);
	}

	@Test
	public void testSendShouldSuccess() throws SendGridException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		Response response = makeResponse(true);
		// when
		when(sendGrid.send(any(Email.class))).thenReturn(response);
		boolean res = sendgridSender.send(request);
		// then
		assertTrue(res);
	}
	
	@Test
	public void testSendShouldReturnFalseWhenSendingFails() throws SendGridException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		Response response = makeResponse(false);
		// when
		when(sendGrid.send(any(Email.class))).thenReturn(response);
		boolean res = sendgridSender.send(request);
		// then
		assertFalse(res);
	}
	
	@Test
	public void testSendShouldReturnFalseOnException() throws SendGridException {
		// given
		MailRequest request = RequestFactory.makeRequest();
		// when
		doThrow(new RuntimeException()).when(sendGrid).send(any(Email.class));
		boolean res = sendgridSender.send(request);
		// then
		assertFalse(res);
	}

	private Response makeResponse(boolean isSuccessfull) {
		int code = isSuccessfull ? 200 : 400;
		return new Response(code, "test");
	}
}
