package com.myallways.mail;

import java.io.InputStream;

import javax.mail.Address;
import javax.mail.Multipart;

import com.myallways.mail.excep.MailSenderException;

public interface Mail {

	String getSubject();
	
	void write(String content);
	
	void write(InputStream in, String charset)  throws Exception;

	Address[] getToAddress() throws MailSenderException;

	Address[] getCcAddress() throws MailSenderException;

	Address[] getBccAddress() throws MailSenderException;

	void addToAddress(String to) throws MailSenderException;

	void addCcAddress(String cc) throws MailSenderException;

	void addBccAddress(String bcc) throws MailSenderException;

	Multipart getMultipart() throws MailSenderException;

	void addAttach(String filepath) throws MailSenderException;

	void addInlineImage(String cid, String filepath) throws MailSenderException;

}
