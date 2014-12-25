package com.myallways.mail.excep;


public class MailSenderException extends Exception {

	private static final long serialVersionUID = 1L;

	public MailSenderException() {

	}
	
	public MailSenderException(String name) {
		super(name);
	}
	
	public MailSenderException(Exception e) {
		super(e);
	}

	public MailSenderException(String name, Throwable e) {
		super(name, e);
	}
}
