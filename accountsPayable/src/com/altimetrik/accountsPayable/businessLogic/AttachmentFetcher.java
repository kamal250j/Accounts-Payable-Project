package com.altimetrik.accountsPayable.businessLogic;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

public class AttachmentFetcher implements Runnable {

	private Properties userCredentials = new Properties();
	private Session session = null;
	private Store store = null;
	private Folder emailFolder = null;
	private String directoryPath = "";

	@Override
	public void run() {

		String pop3Host = "pop.gmail.com";
		String mailStoreType = "pop3";

		while (true) {

			try {
				userCredentials.load(new FileReader("C:\\Users\\kmuwani\\workspace\\"
						+ "accountsPayable\\src\\com\\altimetrik\\"
						+ "accountsPayable\\" + "api\\userCredentials.properties"));

				this.receiveEmail(pop3Host, mailStoreType);

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Some IO Exception occurred ");
			} catch (NoSuchProviderException e2) {
				System.out.println("No such provider exception ");
				e2.printStackTrace();
			} catch (MessagingException e3) {
				System.out.println("Messaging Exception occurred ");
				e3.printStackTrace();
			} catch (CustomException e4) {
				System.out.println(e4.getMessage());
			} finally {
				try {
					emailFolder.close(false);
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	}

	protected void receiveEmail(String pop3Host, String mailStoreType)
			throws NoSuchProviderException, MessagingException, IOException, CustomException {

		System.out.println("Fetching emails ... ");

		Properties props = new Properties();
		props.put("mail.store.protocol", "pop3");
		props.put("mail.pop3.host", pop3Host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");

		Session session = Session.getInstance(props);

		store = session.getStore("pop3s");
		store.connect(pop3Host, userCredentials.getProperty("userName"), userCredentials.getProperty("password"));

		emailFolder = store.getFolder("INBOX");
		emailFolder.open(Folder.READ_ONLY);

		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		Message messages[] = emailFolder.search(unseenFlagTerm);

		if (messages.length == 0) {
//			System.out.println("No messages found ");
		} else
			System.out.println("Total Messages = " + messages.length);

		for (int i = 0; i < messages.length; i++) {
			Message message = messages[i];

			System.out.println("---------------------------------");
			System.out.println("Details of Email Message " + (i + 1) + " :");
			System.out.println("Subject: " + message.getSubject());
			System.out.println("From: " + message.getFrom()[0]);

			// System.out.println("email : " + message.getFrom()[0].toString());
			String[] arr = (message.getFrom()[0].toString()).split(" ");
			String senderEmail = arr[arr.length - 1];
			senderEmail = senderEmail.substring(1, senderEmail.length() - 1);

			System.out.println(message.getContentType());

			if (message.getContentType().contains("multipart")) {
				// System.out.println("Attachment Present ");
				Multipart multipart = (Multipart) message.getContent();
				for (int k = 0; k < multipart.getCount(); k++) {
					MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(k);
					if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
						// this part is attachment
						String fileName = part.getFileName();

						// if(part.getContentType().endsWith(".pdf"))
						// {
						// Address sender=(message.getFrom()[0]);
						// }

						directoryPath = "C:\\Users\\kmuwani\\pdfAttachment\\" + fileName;
						part.saveFile(directoryPath);

						new DataExtractor(directoryPath, senderEmail);

					}
				}

			} else {
				// System.out.println("No attachment present ");
				continue;
			}
		}

	}

}
