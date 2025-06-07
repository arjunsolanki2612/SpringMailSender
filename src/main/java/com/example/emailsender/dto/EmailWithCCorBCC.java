package com.example.emailsender.dto;

import org.springframework.lang.NonNull;

public class EmailWithCCorBCC {

    private String[] to;
    private String[] cc;
    private String[] bcc;
//    @NotNull(message = "Subject is required")
    private String subject;
    private String body;
    private String fileName;
    private String base64File;

    public EmailWithCCorBCC(String[] to, String[] cc, String[] bcc, String subject, String body, String fileName, String base64File) {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
        this.fileName = fileName;
        this.base64File = base64File;
    }
    public String[] getTo() {
        return to;
    }
    public void setTo(String[] to) {
        this.to = to;
    }
    public String[] getCc() {
        return cc;
    }
    public void setCc(String[] cc) {
        this.cc = cc;
    }
    public String[] getBcc() {
        return bcc;
    }
    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getBase64File() {
        return base64File;
    }
    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }
}
