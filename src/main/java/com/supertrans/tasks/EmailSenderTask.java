package com.supertrans.tasks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.supertrans.dto.Mail;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class EmailSenderTask {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("classpath:/STCo.png")
	private Resource resourceFile;

	public Boolean sendEmail(Mail mail) throws MessagingException, IOException {
		try {
			log.info("Sending Email: " + mail.getSubject());
			log.info("Mail Ids :" + mail.getTo());
			log.info("Mail From :" + mail.getFrom());
			Context context = new Context();
			mail.getModel().forEach((name, value) -> context.setVariable(name, value));

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

			String html = templateEngine.process(mail.getTemplate(), context);

			String[] recepients = mail.getTo().split(",");
			helper.setTo(recepients);
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom());
			helper.addInline("attachment.png", resourceFile);

			emailSender.send(message);
			return true;
		} catch (Exception e) {
			log.error("Exception: {}", e);
		}
		return false;
	}

}
