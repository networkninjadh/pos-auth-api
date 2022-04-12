package com.howtech.posauthapi.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//@Service
public class EmailService {

	private final JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void send(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@lastminutehair.com");
		message.setTo(to);
		message.setText(text);
		message.setSubject(subject);
		javaMailSender.send(message);
	}

	public void sendMessageWithAttachment(String to, String subject, String text) {
		// TODO Auto-generated method stub

	}

}
