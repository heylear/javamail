package com.myallways.mail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.myallways.mail.excep.MailSenderException;

public abstract class AbstractMail implements Mail {
	public static final String ADDRESS_SPLIT = ";|,";
	String subject;
	String content;
	List tolist = new ArrayList();
	List cclist = new ArrayList();
	List bcclist = new ArrayList();

	List attaches = new ArrayList();

	Map inlineImages = new HashMap();

	public void addAttach(String filepath) throws MailSenderException {
		attaches.add(new FileDataSource(filepath));
	}

	public void addInlineImage(String cid, String filepath)
			throws MailSenderException {
		inlineImages.put(cid, new FileDataSource(filepath));
	}

	public Multipart getMultipart() throws MailSenderException {
		Multipart multipart = new MimeMultipart();

		addContentBodyPart(multipart);

		addAttachesIfExists(multipart);

		addInlineImagesIfExists(multipart);

		return multipart;
	}

	abstract protected void addContentBodyPart(Multipart multipart)
			throws MailSenderException;

	private void addAttachesIfExists(Multipart multipart)
			throws MailSenderException {
		try {
			for (Iterator iterator = attaches.iterator(); iterator.hasNext();) {
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource ds = (DataSource) iterator.next();
				messageBodyPart.setDataHandler(new DataHandler(ds));
				messageBodyPart.setFileName(ds.getName());
				multipart.addBodyPart(messageBodyPart);
			}
		} catch (MessagingException e) {
			throw new MailSenderException(e);
		}
	}

	private void addInlineImagesIfExists(Multipart multipart)
			throws MailSenderException {
		try {
			for (Iterator iterator = inlineImages.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource ds = (DataSource) entry.getValue();
				messageBodyPart.setDataHandler(new DataHandler(ds));
				messageBodyPart.setHeader("Content-ID", "<" + entry.getKey()
						+ ">");
				multipart.addBodyPart(messageBodyPart);
			}
		} catch (MessagingException e) {
			throw new MailSenderException(e);
		}
	}

	public void addBccAddress(String bcc) throws MailSenderException {
		bcclist.addAll(toInternetAddresses(bcc));
	}

	public void addCcAddress(String cc) throws MailSenderException {
		cclist.addAll(toInternetAddresses(cc));
	}

	public void addToAddress(String to) throws MailSenderException {
		tolist.addAll(toInternetAddresses(to));
	}

	public Address[] getToAddress() throws MailSenderException {
		return (Address[]) tolist.toArray(new InternetAddress[] {});
	}

	public Address[] getCcAddress() throws MailSenderException {
		return (Address[]) cclist.toArray(new InternetAddress[] {});
	}

	public Address[] getBccAddress() throws MailSenderException {
		return (Address[]) bcclist.toArray(new InternetAddress[] {});
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	private List toInternetAddresses(String address) throws MailSenderException {
		List list = new ArrayList();
		StringTokenizer st = new StringTokenizer(address, ADDRESS_SPLIT);
		while (st.hasMoreTokens()) {
			list.add(toInternetAddress(st.nextToken()));
		}
		return list;
	}

	private Address toInternetAddress(String address)
			throws MailSenderException {
		try {
			return new InternetAddress(address);
		} catch (AddressException e) {
			throw new MailSenderException(e);
		}
	}

	public String getContent() {
		return content;
	}

	public void write(String content) {
		this.content = content;
	}

	public void write(InputStream in, String charset) throws Exception {
		InputStreamReader isr = new InputStreamReader(in, charset);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
			sb.append(line);
		this.content = sb.toString();
	}

}
