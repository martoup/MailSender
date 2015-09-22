package com.github.mailsender.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.mailsender.RequestFactory;
import com.github.mailsender.sender.MailSender;
import com.github.mailsender.sender.model.MailRequest;
import com.github.mailsender.sender.model.MailResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContextConfiguration.class })
public class MailSenderControllerTest {

	@Autowired
	private List<MailSender> mailSenders;

	@Autowired
	private MailSenderController controller;

	@Test
	public void testResponseShouldBeSuccessfullIfAtLeastOneSendIsSuccessfull() {
		// given
		MailRequest request = RequestFactory.makeRequest();
		// when
		makeAtLeastOneSendSuccessful(request);
		ResponseEntity<MailResponse> res = controller.sendMail(request);
		// then
		assertEquals(MailResponse.SUCCESSFUL.getStatus(), res.getStatusCode());
		assertResponse(MailResponse.SUCCESSFUL, res.getBody());
	}

	@Test
	public void testResponseShouldFailIfAllSendersFail() {
		// given
		MailRequest request = RequestFactory.makeRequest();
		// when
		for (MailSender sender : mailSenders)
			when(sender.send(request)).thenReturn(false);
		ResponseEntity<MailResponse> res = controller.sendMail(request);
		// then
		assertEquals(MailResponse.UNSUCCESSFUL.getStatus(), res.getStatusCode());
		assertResponse(MailResponse.UNSUCCESSFUL, res.getBody());
	}

	private void makeAtLeastOneSendSuccessful(MailRequest request) {
		// Make all senders but the last one to fail at sending:
		int lastSenderIndex = mailSenders.size() - 1;
		for (int i = 0; i < lastSenderIndex; i++)
			when(mailSenders.get(i).send(request)).thenReturn(false);
		when(mailSenders.get(lastSenderIndex).send(request)).thenReturn(true);
	}

	private void assertResponse(MailResponse expected, MailResponse actual) {
		assertEquals(expected.getMessage(), expected.getMessage());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.isSuccess(), actual.isSuccess());
	}
}
