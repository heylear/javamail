package com.myallways.mail;

import com.myallways.mail.excep.MailSenderException;

public class SenderFactory {
	static final String SENDER_CREATOR = "com.myallways.mail.SenderCreator";
	static SenderCreator creator;

	public static SenderCreator getInstance() throws MailSenderException {
		if (creator == null) {
			try {
				creator = (SenderCreator) Class.forName(SENDER_CREATOR).newInstance();
			} catch (Exception e) {
				throw new MailSenderException("can't init sender", e);
			}
		}
		return creator;
	}

}
