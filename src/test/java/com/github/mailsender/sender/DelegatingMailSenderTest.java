package com.github.mailsender.sender;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

import com.github.mailsender.RequestFactory;
import com.github.mailsender.sender.model.MailRequest;
import com.github.mailsender.sender.model.MailResponse;

public class DelegatingMailSenderTest {

	private DelegatingMailSender sender;
	
	@Test
	public void testSendMailIsSuccessfullIfAtLeastOneIsSuccess()
	{
		//given
		sender = new DelegatingMailSender(Arrays.asList(makeSender(false), makeSender(true)));
		//when
		MailResponse response = sender.sendMail(RequestFactory.makeRequest());
		//then
		assertTrue(response.isSuccess());
		assertEquals(MailResponse.SUCCESSFUL.getStatus(), response.getStatus());
		assertEquals(MailResponse.SUCCESSFUL.getMessage(), response.getMessage());
	}
	@Test
	public void testSendMailFailedIfNoSenderSucceed()
	{
		//given
		sender = new DelegatingMailSender(Arrays.asList(makeSender(false), makeSender(false)));
		//when
		MailResponse response = sender.sendMail(RequestFactory.makeRequest());
		//then
		assertFalse(response.isSuccess());
		assertEquals(MailResponse.UNSUCCESSFUL.getStatus(), response.getStatus());
		assertEquals(MailResponse.UNSUCCESSFUL.getMessage(), response.getMessage());
	}
	
	private MailSender makeSender(boolean isSuccessfull)
	{
		MailSender ms = mock(MailSender.class);
		when(ms.send(any(MailRequest.class))).thenReturn(isSuccessfull);
		return ms;
	}
	
}
