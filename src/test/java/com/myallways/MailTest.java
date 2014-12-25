package com.myallways;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.myallways.mail.HtmlTemplateMail;
import com.myallways.mail.Sender;
import com.myallways.mail.SenderFactory;
import com.myallways.mail.excep.MailSenderException;

/**
 * Unit test for simple App.
 */
public class MailTest extends TestCase {
	Sender sender;

	protected void setUp() throws Exception {
		sender = SenderFactory.getInstance().createDefaultSender();
	}

	/*
	 * public void testTextMail() throws MailSenderException { Mail mail = new
	 * TextMail("test message"); mail.addToAddress("heylear@126.com");
	 * mail.write("this is text email by newsletter@mysvw.com");
	 * 
	 * mail.addAttach("D:\\hlc\\rep\\mvn\\javax\\mail\\mail\\1.4\\mail-1.4.jar");
	 * sender.send(mail); }
	 */

	public void testHtmlMail() throws MailSenderException {
		HtmlTemplateMail mail = new HtmlTemplateMail("template mail");
		
		mail.write("<html><body><h1>hello,<vusername>,this is a hello mail!<h1></body></html>");
		List list = new ArrayList();
		list.add(new HtmlTemplateMail.Entry("vusername","hailiangc"));
		mail.setModel(list);
		System.out.println(mail.parseTemplate());
		/*Mail mail = new HtmlMail("test message");
		mail.addToAddress("heylear@126.com,heylear@foxmail.com;hailiangc@myallways.cn");
		mail.write("<H1>this is text email by newsletter@mysvw.com</H1><img src=\"cid:image\">");
		mail.addInlineImage("image",
				"D:\\hlc\\rep\\front\\ace\\assets\\images\\gallery\\image-2.jpg");
		sender.send(mail);*/
	}
}
