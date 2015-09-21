package com.github.mailsender.utils;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.mailsender.RequestFactory;
import com.github.mailsender.sender.model.MailRequest;

public class RequestValidatorTest {

	private static final int MAX_RECIPIENTS = 10;

	private static final int MAX_SUBJECT_LENGTH = 20;
	
	private RequestValidator validator;
	
	private String[] validEmails = new String[] { "test@yahoo.com",
			"test-100@yahoo.com", "test.100@yahoo.com",
			"test111@test.com", "test-100@test.net",
			"test.100@test.com.au", "test@1.com",
			"test@gmail.com.com", "test+100@gmail.com",
			"test-100@yahoo-test.com" };
	
	private String[] invalidEmails = new String[] { "test", "test@.com.my",
			"test123@gmail.a", "test123@.com", "test123@.com.com",
			".test@test.com", "test()*@gmail.com", "test@%*.com",
			"test..2002@gmail.com", "test.@gmail.com",
			"test@test@gmail.com", "test@gmail.com.1a" };
	
	@Before
	public void setUp()
	{
		validator = new RequestValidator(MAX_RECIPIENTS, MAX_SUBJECT_LENGTH);
	}
	
	//validateEmailAddress()
	@Test
	public void testValidateEmailAddressSuccessfull()
	{
		for(String email : validEmails)
		{
			validator.validateEmailAddress(email);
		}
	}
	@Test
	public void testValidateEmailAddressUnSuccessfull()
	{
		for(String email : invalidEmails)
		{
			try{
			validator.validateEmailAddress(email);
			Assert.fail();
			}catch(IllegalArgumentException e){
				//ok
			}
		}
	}
	
	//validateRequest
	@Test
	public void testValidateRequestShouldPass()
	{
		//given
		MailRequest request = RequestFactory.makeRequest();
		//when
		validator.validateRequest(request);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testValidateRequestShouldFailSumOfRecipientsIsMoreThanLimit()
	{
		//given
		MailRequest request = RequestFactory.makeRequest();
		//request is with 3 recipients, set max to 2
		validator = new RequestValidator(2, MAX_SUBJECT_LENGTH);
		//when
		validator.validateRequest(request);
	}

	//validateRecipientsLimit()
	@Test
	public void validateRecipientsShouldPassIfRecipientsAreLessThanLimit()
	{
		validator.validateRecipientsLimit(MAX_RECIPIENTS);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateRecipientsShouldFailIfRecipientsAreMoreThanLimit()
	{
		validator.validateRecipientsLimit(MAX_RECIPIENTS+1);
	}
	
	//validateTo()
	@Test
	public void validateToShouldPass()
	{
		validator.validateTo(Arrays.asList("test@email.com"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateToShouldFailIfParamIsNull()
	{
		validator.validateTo(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateToShouldFailIfParamIsAnEmptyList()
	{
		validator.validateTo(Collections.emptyList());
	}

	//validateFrom()
	@Test
	public void validateFromShouldPass()
	{
		validator.validateFrom("test@email.com");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateFromShouldFailIfParamIsNull()
	{
		validator.validateFrom(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateFromShouldFailIfFromIsAnEmptyString()
	{
		validator.validateFrom("");
	}
	
	//validateNonRequieredEmailFileds()
	@Test
	public void validateNonRequieredEmailFiledsShouldPass()
	{
		validator.validateNonRequieredEmailFileds(Arrays.asList("test@email.com"));
	}
	
	@Test
	public void validateNonRequieredEmailFiledsShouldPassIfParamIsAnEmptyList()
	{
		validator.validateNonRequieredEmailFileds(Collections.emptyList());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateNonRequieredEmailFiledsShouldFailIfParamIsNull()
	{
		validator.validateNonRequieredEmailFileds(null);
	}
	
	//validateSubject()
	@Test
	public void validateSubjectShouldPass()
	{
		validator.validateSubject("test");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateSubjectShouldFailIfParamIsNull()
	{
		validator.validateSubject(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateSubjectShouldFailIfSubjectIsAnEmptyString()
	{
		validator.validateSubject("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateSubjectShouldFailIfParamIsLongerThanLimit()
	{
		//given
		StringBuilder builder = new StringBuilder(MAX_SUBJECT_LENGTH+1);
		for (int i = 0; i < MAX_SUBJECT_LENGTH+1; i++) {
			builder.append("a");
		}
		//when
		validator.validateSubject(builder.toString());
	}

	//validateContent()
	@Test
	public void validateContentShouldPass()
	{
		validator.validateContent("test");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateContentShouldFailIfParamIsNull()
	{
		validator.validateContent(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void validateContentShouldFailIfContentIsAnEmptyString()
	{
		validator.validateContent("");
	}
}
