package com.howtech.posauthapi.config;

import java.util.Properties;

import javax.mail.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	final private String HOST = System.getenv("MAIL_HOST");
	final private int PORT = Integer.parseInt(System.getenv("MAIL_PORT"));
	final private String USERNAME = System.getenv("MAIL_USERNAME");
	final private String PASSWORD = System.getenv("MAIL_PASSWORD");

	@Bean
	JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(HOST);
		mailSender.setPort(PORT);
		mailSender.setUsername(USERNAME);
		mailSender.setPassword(PASSWORD);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		Session session = Session.getInstance(props);
		mailSender.setSession(session);

		return mailSender;
	}
}