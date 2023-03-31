package com.ftn.security.smarthomebackend.service.implementation;

import com.ftn.security.smarthomebackend.exception.MailCannotBeSentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;

import static com.ftn.security.smarthomebackend.util.Constants.TEMPLATE_FILE_PATH;
import static com.ftn.security.smarthomebackend.util.EmailConstants.*;

@Service
public class EmailService {

    private final Environment env;

    public EmailService(final Environment env) {
        this.env = env;
    }

    @Async
    public void sendVerificationMail(int verificationCode, String verificationUrl)
            throws MailException, IOException, MailCannotBeSentException {
        String pathToHTMLFile = TEMPLATE_FILE_PATH + "sendVerificationMailTemplate.html";
        String emailTemplate_PartOne = setEmailTemplate(pathToHTMLFile, "_verificationUrl_", verificationUrl);
        String emailTemplate = splitAndConcatenate(emailTemplate_PartOne, "_verificationCode_", Integer.toString(verificationCode));
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        HTMLEmailService mm = (HTMLEmailService) context.getBean("htmlMail");
        mm.sendMail(EMAIL, EMAIL, SUBJECT_VERIFY_USER, emailTemplate);
    }

    @Async
    public void sendEmailWithCertificate(File file) throws CertificateParsingException, CertificateEncodingException, KeyStoreException, MailCannotBeSentException, MessagingException, IOException {
        String html = "<!doctype html>\n" +
                "<html ⚡4email data-css-strict>\n" +
                "\n" +
                "<head><meta charset=\"utf-8\"></head>" +
                "<body>" +
                "Dear user,<br/><br/> " +
                "We would notify you about acceptance of Your request for certificate.<br/>" +
                "We send You certificate in the attachment.<br/>" +
                "Best regards,<br/><br/>" +
                "Smart Home Team" +
                "</body>\n" +
                "\n" +
                "</html>";

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        HTMLEmailService mm = (HTMLEmailService) context.getBean("htmlMail");

        mm.sendMailWithAttachment(EMAIL, EMAIL,
                String.format("%s", CERTIFICATE),
                html,
                file

        );
    }

    private String readHtmlFile(String filePath) throws IOException {

        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private String setEmailTemplate(String pathToHTMLFile, String regexForSplit, String parameter) throws IOException {
        String readHtmlString = readHtmlFile(pathToHTMLFile);

        return splitAndConcatenate(readHtmlString, regexForSplit, parameter);
    }


    private String splitAndConcatenate(String stringForSplit, String regexForSplit, String parameterForConcatenate) {
        String[] splittedString = stringForSplit.split(regexForSplit);

        return splittedString[0] + parameterForConcatenate + splittedString[1];
    }

}
