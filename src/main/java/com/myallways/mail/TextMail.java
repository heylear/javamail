package com.myallways.mail;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import com.myallways.mail.excep.MailSenderException;

public class TextMail extends AbstractMail {

	public TextMail(String subject) {
		this.subject = subject;
	}

	public TextMail(String subject, String to) throws MailSenderException {
		addToAddress(to);
		this.subject = subject;
	}

	public TextMail(String subject, String content, String to)
			throws MailSenderException {
		addToAddress(to);
		this.subject = subject;
		this.content = content;
	}

	public void addContentBodyPart(Multipart multipart)
			throws MailSenderException {
		try {
			if (content == null || content.trim().equals(""))
				return;
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(content);
			multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {
			throw new MailSenderException(e);
		}
	}

}
