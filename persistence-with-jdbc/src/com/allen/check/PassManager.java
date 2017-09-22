package com.allen.check;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;

import com.allen.NameHashTable;

public class PassManager {
//	public static void main(String[] args) {
//		Check c = new Check();
//		System.out.println(c.generateNewPassword());
//		
//	}
	
	private String username;
	private String password;
	private Map<String, String> repo = new HashMap<>();
	
	public PassManager() {
		setRepo();
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
	
	public String generateNewPassword() {
		return getSaltString();
	}
	
	// https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
	protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	public Map<String, String> getRepo() {
		return repo;
	}
	
	public void setRepo() {
		NameHashTable.initHash();
		
		NameHashTable.hash.forEach((key, value) -> {
		    repo.put(key, generateNewPassword());
		});
	}
	
	public void sendEmail(String username) {
		try{
            String emailContentFilePath = "resource/email content.txt";
            String host ="mail.sap.corp" ;
            String from = "allen.qian@sap.com";
            String subject = "Queue Manager Automatically Send.";
            String messageText = "username: " + username + "; password: " + repo.get(username);
            boolean sessionDebug = false;

            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "25");
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);

            // send multiple emails
           
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(emailAdress)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);
            Transport.send(msg);
        

            System.out.println("message send successfully");
        }catch(Exception ex)  {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }
	}
}
