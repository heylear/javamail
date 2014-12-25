package com.myallways.mail;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import com.myallways.mail.excep.MailSenderException;

public class HtmlMail extends AbstractMail {
	
	private String contentType = "text/html;charset=utf-8";
	
	public HtmlMail(String subject) {
		this.subject = subject;
	}

	public HtmlMail(String subject, String to) throws MailSenderException {
		addToAddress(to);
		this.subject = subject;
	}

	public HtmlMail(String subject, String content, String to)
			throws MailSenderException {
		addToAddress(to);
		this.subject = subject;
		this.content = content;
	}
	
	protected void addContentBodyPart(Multipart multipart)
			throws MailSenderException {
		try {
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, contentType);
			multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {
			throw new MailSenderException(e);
		}
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
