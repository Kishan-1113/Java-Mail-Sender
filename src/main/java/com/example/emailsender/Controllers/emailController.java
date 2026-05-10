package com.example.emailsender.Controllers;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.emailsender.Models.EmailSend;
import com.example.emailsender.Models.User;
import com.example.emailsender.Publisher.RabbitJsonProducer;
import com.example.emailsender.Publisher.RabbitProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private BlockingQueue<EmailSend> queue;

    @Autowired
    private RabbitProducer rabbitProducer;

    @Autowired
    private RabbitJsonProducer rabbitJsonProducer;

    @GetMapping("/rabbit/string{message}")
    public ResponseEntity<String> getMethodName(@RequestParam String message) {
        rabbitProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ server... ");
    }

    @PostMapping("/rabbit/json")
    public ResponseEntity<String> getMethodName(@RequestBody User user) {
        rabbitJsonProducer.sendMessage(user);
        return ResponseEntity.ok("Json message sent to RabbitMQ server...");
    }

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