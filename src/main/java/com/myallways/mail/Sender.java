package com.myallways.mail;

import com.myallways.mail.excep.MailSenderException;

public interface Sender {
	static final String MAIL_AUTH_PRO_KEY = "mail.smtp.auth";

	void send(Mail mail) throws MailSenderException;
}
