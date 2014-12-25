package com.myallways.mail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import com.myallways.mail.excep.MailSenderException;

public class HtmlTemplateMail extends AbstractMail {

	private Object model;

	private String contentType = "text/html;charset=utf-8";

	private String HTML_REG = "<(S*?)[^>]*>.*?|<.*?/>";

	public HtmlTemplateMail(String subject) {
		this.subject = subject;
	}

	public HtmlTemplateMail(String subject, String to)
			throws MailSenderException {
		addToAddress(to);
		this.subject = subject;
	}

	public HtmlTemplateMail(String subject, String content, String to)
			throws MailSenderException {
		addToAddress(to);
		this.subject = subject;
		this.content = content;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	protected void addContentBodyPart(Multipart multipart)
			throws MailSenderException {
		try {
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(parseTemplate(), contentType);
			multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {
			throw new MailSenderException(e);
		}
	}

	public String parseTemplate() throws MailSenderException {
		if (Pattern.matches(HTML_REG, content)) {
			return replaceContent(content);
		} else {
			return replaceFile(content);
		}
	}

	protected String replaceFile(String pathname) throws MailSenderException {
		try {
			FileInputStream in = new FileInputStream(pathname);
			InputStreamReader isr = new InputStreamReader(in, getCharset());
			BufferedReader br = new BufferedReader(isr);
			StringBuffer res = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null)
				res.append(line).append("\n");
			return replaceContent(res.toString());
		} catch (IOException e) {
			throw new MailSenderException("can't load template file: "
					+ pathname);
		}
	}

	private String getCharset() {
		Matcher m = Pattern.compile("charset=([\\w-]+)",
				Pattern.CASE_INSENSITIVE).matcher(contentType);
		if (m.find())
			return m.group(1);
		return null;
	}

	protected String replaceContent(String content) {
		if (model == null)
			return content;
		if (model instanceof List)
			return replaceContentByEntry(content, (List) model);
		else
			return replaceContentByFreeMarker();
	}

	private String replaceContentByFreeMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	private String replaceContentByEntry(String content, List entries) {
		for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			content = replace(content, entry);
		}
		return content;
	}

	private String replace(String content, Entry entry) {
		Matcher m = Pattern.compile(entry.toString(), Pattern.CASE_INSENSITIVE)
				.matcher(content);
		while (m.find()){
			content = content.replaceFirst(m.group(), entry.getValue());
		}
		return content;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public static class Entry {
		public static final String DEFAULT_KEY_PREFIX = "<";
		public static final String DEFAULT_KEY_SUFFIX = ">";
		String keyPrefix = DEFAULT_KEY_PREFIX;
		String keySuffix = DEFAULT_KEY_SUFFIX;
		String key;
		String value;

		public Entry() {
			super();
		}

		public Entry(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public String getKeyPrefix() {
			return keyPrefix;
		}

		public void setKeyPrefix(String keyPrefix) {
			this.keyPrefix = keyPrefix;
		}

		public String getKeySuffix() {
			return keySuffix;
		}

		public void setKeySuffix(String keySuffix) {
			this.keySuffix = keySuffix;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String toString() {
			return keyPrefix + key + keySuffix;
		}
	}
}
