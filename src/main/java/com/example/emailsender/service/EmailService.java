package com.example.emailsender.service;

import com.example.emailsender.dto.EmailWithCCorBCC;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;


//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }

    public void sendMail(String to, String subject, String body) {
        // SimpleMailMessage message = new SimpleMailMessage(); --> it will create an empty email message object
        // like a template that has to, subject, body but it is blank. it only sends plain text
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("arjunsolanki2148@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);


    }

    public void sendHtmlMail(String to, String subject, String body) throws MessagingException {
       // MimeMessage message = mailSender.createMimeMessage(); --> it will create an empty email message object
        // like a template that has to, subject, body but it is blank.
        MimeMessage message = mailSender.createMimeMessage();

        // this helper object will help us to set the content type and setting the constructor to true means that
        // the email will be sent in html format
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("arjunsolanki2148@gmail.com"); // it is optional if we don't want to set the from application.properties
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(message);
    }

    public void sendMailWithAttachment(String to, String subject, String body, File file) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("arjunsolanki2148@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);


        /*
        file — ye wahi temporary file hai jo humne base64 decode karke banayi thi.
        FileSystemResource — ye ek Spring class hai jo file ko email ke attachment ke format me convert karta
        file.getName() — ye file ka naam hai, e.g., "resume.pdf", "image.jpg"
🔸      fileResource — ye woh resource hai jise ab attach kiya ja raha hai.

        Java ki normal File class email me attach nahi ki ja sakti directly.
        Toh FileSystemResource usko proper format me laata hai taaki MimeMessageHelper usse accept kar sake.
        */
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        helper.addAttachment(file.getName(), fileSystemResource);
        mailSender.send(message);
    }


    public void sendMailWithCCorBCC(EmailWithCCorBCC emailWithCCorBCC) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("arjunsolanki2148@gmail.com");
        if (emailWithCCorBCC.getSubject() != null)
            helper.setSubject(emailWithCCorBCC.getSubject());

        helper.setText(emailWithCCorBCC.getBody(), true);
        if(emailWithCCorBCC.getTo() != null) helper.setTo(emailWithCCorBCC.getTo());
        if(emailWithCCorBCC.getCc() != null) helper.setCc(emailWithCCorBCC.getCc());
        if(emailWithCCorBCC.getBcc() != null) helper.setBcc(emailWithCCorBCC.getBcc());

        // Decode and attach file if exists
        if (emailWithCCorBCC.getBase64File() != null && emailWithCCorBCC.getFileName() != null) {
            byte[] fileBytes = Base64.getDecoder().decode(emailWithCCorBCC.getBase64File());
            File tempFile = File.createTempFile("upload-", emailWithCCorBCC.getFileName());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(fileBytes);
            }
            FileSystemResource fileResource = new FileSystemResource(tempFile);
            helper.addAttachment(tempFile.getName(), fileResource);
        }

        mailSender.send(message);
    }

}
