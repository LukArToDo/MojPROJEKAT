package company.Main.Support.Izvodi;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

public class EmailAttachmentReceiver {
	/**
	 * Downloads new messages and saves attachments to disk if any.
	 * @throws ParseException 
	 * @throws MessagingException 
	 */
	public static Boolean downloadEmailAttachments(String senderAdress, String userName, String password, LocalDate ld, int q) throws ParseException, MessagingException {
		boolean conn=false;
		String host = "imap.gmail.com";
		String port = "993";
		String saveDirectory = "C:/MojProjekat/Attachment";

		File dir=new File("C:/MojProjekat/Attachment");
		dir.mkdirs();
	
		for(File file: dir.listFiles()) if(!file.isDirectory()) file.delete();
	
		Message[] arrayMessages=null;
		Message message=null;
	
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);
		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
            "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
            String.valueOf(port));
		properties.put("mail.imap.starttls.enable", "true");
		Session session = Session.getDefaultInstance(properties);
		// connects to the message store
		Store store = session.getStore("imap");
		try{
			store.connect(userName, password);
   
			if (store.isConnected()){
				conn=true;
				// opens the inbox folder
				Folder folderInbox = store.getFolder("INBOX");
				folderInbox.open(Folder.READ_ONLY);
    
				try {
					for (int d=0; d<q+1; d++){
						if (message==null){
							arrayMessages = folderInbox.search(setTerms(d,senderAdress, ld));

							for (int i = 0; i < arrayMessages.length; i++) {
								message = arrayMessages[i];
								Address[] fromAddress = message.getFrom();
								String from = fromAddress[0].toString();
								String subject = message.getSubject();
								String sentDate = message.getSentDate().toString();
								String contentType = message.getContentType();
								String messageContent = "";
           
								// store attachment file name, separated by comma
								String attachFiles = "";
								if (sentDate.endsWith("2017")){
									if (contentType.contains("multipart")) {
										// content may contain attachments
										Multipart multiPart = (Multipart) message.getContent();
										int numberOfParts = multiPart.getCount();
										for (int partCount = 0; partCount < numberOfParts; partCount++) {
											MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
											if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
												// this part is attachment
												String fileName = subject;
												attachFiles += fileName + ", ";
												part.saveFile(saveDirectory + "/" + fileName+".pdf");
												} else {
													// this part may be the message content
													messageContent = part.getContent().toString();
												}
										}

										if (attachFiles.length() > 1) {
											attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
										}
									} else if (contentType.contains("text/plain")|| contentType.contains("text/html")) {
										Object content = message.getContent();
										if (content != null) {
											messageContent = content.toString();
										}
									}
								}
								// print out details of each message
								System.out.println("Message #" + (i + 1) + ":");
								System.out.println("\t From: " + from);
								System.out.println("\t Subject: " + subject);
								System.out.println("\t Sent Date: " + sentDate);
								System.out.println("\t Message: " + messageContent);
								System.out.println("\t Attachments: " + attachFiles); 
							} message=null;
						}
					}folderInbox.close(false);
					store.close();
				} catch (NoSuchProviderException ex) {
					System.out.println("No provider for pop3.");
					ex.printStackTrace();
				} catch (MessagingException ex) {
					System.out.println("Could not connect to the message store");
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch(AuthenticationFailedException ae){
			ae.getMessage();
			conn=false;
		}
		return conn;
	}

	/**
	 * Set terms of searching mail
	 * @throws AddressException 
	 * @throws ParseException 
	 * @throws MessagingException 
	 */
	private static SearchTerm setTerms (int d, String senderAdress, LocalDate ld) throws AddressException, ParseException{
		SearchTerm sender = new FromTerm(new InternetAddress(senderAdress));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = ld.minusDays(d);
		System.out.println(dtf.format(localDate)); 
     
		String dt=dtf.format(localDate);
		SimpleDateFormat df1 = new SimpleDateFormat( "MM/dd/yyyy" );
		java.util.Date dDate = df1.parse(dt);
   
		SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.EQ, dDate);
		FlagTerm flag = new FlagTerm( new Flags(Flags.Flag.SEEN), true); 
		SearchTerm andTerm0 = new AndTerm(flag, sender);
		SearchTerm andTerm = new AndTerm(andTerm0, newerThan);
     
		return andTerm;
	}
}
