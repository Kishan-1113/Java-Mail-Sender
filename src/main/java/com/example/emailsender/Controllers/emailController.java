package com.example.emailsender.Controllers;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.emailsender.Models.EmailSend;

@RestController
@RequestMapping("/email")
public class emailController {

    @Autowired
    private BlockingQueue<EmailSend> queue;

    @GetMapping("/health")
    public String health() {
        return new String("Hello, how are you? I am fine !");
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailSend emailSend) {
        queue.offer(emailSend);
        return ResponseEntity.ok("Queued !");
    }
}