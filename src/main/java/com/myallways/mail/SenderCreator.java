package com.myallways.mail;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.myallways.mail.excep.MailSenderException;

public class SenderCreator {
	static final Log log = LogFactory.getLog(SenderCreator.class);
	static Properties pro;
	
	static {
		pro = new Properties();
		try {
			SenderCreator.class.getClassLoader();
			pro.load(ClassLoader.getSystemResourceAsStream("mail_conf.properties"));
		} catch (IOException e) {
			log.error("can't find mail configuration file such as mail_conf.properties");
		}
		
	}
	
	public Sender createDefaultSender() throws MailSenderException{
		return new MailSender(new DefaultMailAuthor(pro));
	}
	
	public Sender createSender(Author author) throws MailSenderException{
		return new MailSender(author);
	}
}
