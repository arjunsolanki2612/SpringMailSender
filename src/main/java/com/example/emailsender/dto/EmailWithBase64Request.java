package com.example.emailsender.dto;

public class EmailWithBase64Request {
    private String to;
    private String subject;
    private String body;
    private String fileName;
    private String base64File;

    public EmailWithBase64Request(String to, String base64Content, String fileName, String body, String subject) {
        this.to = to;
        this.base64File = base64Content;
        this.fileName = fileName;
        this.body = body;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
