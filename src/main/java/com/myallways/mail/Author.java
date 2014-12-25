package com.myallways.mail;

import java.util.Properties;

import javax.mail.Address;

import com.myallways.mail.excep.MailSenderException;

public interface Author {

	Properties getMailConfiguration();

	String getUsername();

	String getPassword();

	String getFromname();

	Address getFromaddress() throws MailSenderException;
}
