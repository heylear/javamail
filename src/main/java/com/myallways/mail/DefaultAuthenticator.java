package com.myallways.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class DefaultAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public DefaultAuthenticator() {
	}

	public DefaultAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

}
