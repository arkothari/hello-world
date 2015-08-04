package com.abhinav.datafeed.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailUtil {

	@Autowired
    private JavaMailSender mailSender;
	
	public String sendEmail(List<String> emails,String subject, String message){
		String recipientAddress = "ar.kothari@gmail.com";
	
	    // prints debug info
	    System.out.println("To: " + recipientAddress);
	    System.out.println("Subject: " + subject);
	    System.out.println("Message: " + message);
	     
	    // creates a simple e-mail object
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setTo(recipientAddress);
	    email.setSubject(subject);
	    email.setText(message);
	    
	    mailSender.send(email);
	    return null;
	}
 
}
