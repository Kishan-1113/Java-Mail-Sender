package com.example.emailsender.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.emailsender.Services.MailService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/email")
public class emailController {

    @Autowired
    private MailService emailService;

    @GetMapping("/health")
    public String health() {
        return new String("Hello, how are you? I am fine !");
    }

    @PostMapping("/send")
    public String sendMail(@RequestBody String to) {
        emailService.sendSimpleEmail(
                to,
                "Test Email",
                "Hello, your email is working successfully!");
        return "Email sent!";
    }
}