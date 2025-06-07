package com.example.emailsender.controller;

import com.example.emailsender.dto.EmailWithBase64Request;
import com.example.emailsender.dto.EmailWithCCorBCC;
import com.example.emailsender.model.EmailRequest;
import com.example.emailsender.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest){
       try{
           emailService.sendMail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
           return "Email sent successfully" + emailRequest.getBody();
       } catch (Exception e) {
           e.printStackTrace();
           return "Failed to send email" + e.getMessage();
       }

    }

    @PostMapping("/send-html")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {

        try{
            emailService.sendHtmlMail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
            return ResponseEntity.ok("Email with HTML contentsent successfully" + emailRequest.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send email" + e.getMessage());
        }
    }

    @PostMapping("/send-base64")
    public ResponseEntity<String> sendMailwithAttachment(@RequestBody EmailWithBase64Request request) throws IOException {
        try {
            //storing the base64 that we get from front end or from postman int bytes and saving it into the memory
            byte[] fileBytes = Base64.getDecoder().decode(request.getBase64File());

            // creating a tempfile to write the content of fileBytes into it
            File tempFile = File.createTempFile("upload-", request.getFileName());

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(fileBytes);
            }

            emailService.sendMailWithAttachment(request.getTo(), request.getSubject(), request.getBody(), tempFile);
            return ResponseEntity.ok("Email with base 64 attachment sent successfully" + request.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send email" + e.getMessage());
        }


    }

    @PostMapping("send-ccORbcc")
    public ResponseEntity<String> sendMailWithCCorBCC(@RequestBody EmailWithCCorBCC emailWithCCorBCC) throws MessagingException {
        try {
            emailService.sendMailWithCCorBCC(emailWithCCorBCC);
            return ResponseEntity.ok("Email with CC or BCC sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send email" + e.getMessage());
        }

    }
}
