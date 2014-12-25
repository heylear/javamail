package com.myallways.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.myallways.mail.excep.MailSenderException;

public class DefaultMailAuthor implements Author {

	Properties mailConfiguration;
	String username;
	String password;
	String fromname;
	String fromaddress;

	public DefaultMailAuthor() {
	}

	public DefaultMailAuthor(Properties pro) {
		this.mailConfiguration = pro;
		this.username = mailConfiguration.getProperty("username");
		this.password = mailConfiguration.getProperty("password");
		this.fromname = mailConfiguration.getProperty("fromname");
		this.fromaddress = mailConfiguration.getProperty("fromaddress");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromname() {
		return fromname;
	}

	public void setFromname(String fromname) {
		this.fromname = fromname;
	}

	public Address getFromaddress() throws MailSenderException {
		try {
			return new InternetAddress(fromaddress);
		} catch (AddressException e) {
			throw new MailSenderException(e);
		}
	}

	public void setFromaddress(String fromaddress) {
		this.fromaddress = fromaddress;
	}

	public void setMailConfiguration(Properties mailConfiguration) {
		this.mailConfiguration = mailConfiguration;
	}

	public Properties getMailConfiguration() {
		return mailConfiguration;
	}
}
