package com.myallways.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.myallways.mail.excep.MailSenderException;

public class MailSender implements Sender {

	public static final Log log = LogFactory.getLog(MailSender.class);

	Author author;

	public MailSender(Author author) throws MailSenderException {
		this.author = author;
	}

	public void send(Mail mail) throws MailSenderException {
		try {
			Session session = createSession();
			Message message = initMessage(session, mail);
			Transport.send(message);
		} catch (MessagingException e) {
			log.error("mail send failed!", e);
			throw new MailSenderException("mail send failed!", e);
		}
	}

	private Message initMessage(Session session, Mail mail)
			throws MailSenderException {
		try {
			Message message = new MimeMessage(session);

			message.setRecipients(Message.RecipientType.TO, mail.getToAddress());
			message.setRecipients(Message.RecipientType.CC, mail.getCcAddress());
			message.setRecipients(Message.RecipientType.BCC, mail.getBccAddress());

			message.setSubject(mail.getSubject());
			message.setFrom(author.getFromaddress());
			
			message.setContent(mail.getMultipart());
			
			message.saveChanges();
			return message;
		} catch (MessagingException e) {
			throw new MailSenderException(e);
		}
	}

	private Session createSession() {
		Properties pro = author.getMailConfiguration();
		Authenticator authenticator = null;
		if (Boolean.valueOf((String) pro.get(MAIL_AUTH_PRO_KEY)).booleanValue()) {
			authenticator = new DefaultAuthenticator(author.getUsername(),
					author.getPassword());
		}
		return Session.getInstance(pro, authenticator);
	}
}
