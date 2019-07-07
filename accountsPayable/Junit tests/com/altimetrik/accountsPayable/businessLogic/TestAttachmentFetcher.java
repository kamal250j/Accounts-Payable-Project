package com.altimetrik.accountsPayable.businessLogic;

import javax.mail.MessagingException;

import org.junit.Test;

public class TestAttachmentFetcher {

	@Test(expected = MessagingException.class) 
	public void  testReceiveEmail() throws MessagingException, Exception{
		AttachmentFetcher obj = new AttachmentFetcher();
		obj.receiveEmail("some random string","some random string");
	}
	
}
