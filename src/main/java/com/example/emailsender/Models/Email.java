package com.example.emailsender.Models;

import lombok.Data;

@Data
public class Email {
    private String sendTo;
    private String subject;
    private String content;

}
