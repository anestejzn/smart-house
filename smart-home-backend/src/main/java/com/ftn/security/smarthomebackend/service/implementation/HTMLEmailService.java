package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.exception.MailCannotBeSentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTMLEmailService {

    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) throws MailCannotBeSentException {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(msg, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new MailCannotBeSentException(from);
        }
    }

    public void sendMailWithAttachment(String from, String to, String subject, String body, File attachment) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        helper.addAttachment("attachment.txt", new ByteArrayResource(Files.readAllBytes(attachment.toPath())));

//        helper.getMimeMessage().getAttachments()[0].addHeader("Content-Disposition", "attachment");

        helper.setFrom(from);

        mailSender.send(message);
    }

}
